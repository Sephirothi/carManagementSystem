package com.carManage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransientObjectException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.dao.BaseDAO.NULL;
import com.carManage.model.Car;
import com.carManage.model.CarTransfer;
import com.carManage.model.CarUser;

import edu.emory.mathcs.backport.java.util.LinkedList;

/**
 * 车辆查询页面 限制条件:无 （未测试）
 * 
 * @author admin
 *
 */
@Repository("carDAOImpl")
public class CarDAOImpl extends BaseDAO<Car, NULL> {

	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public boolean update(Car car) {
		Session session = null;
		try {
			// 获取到车排号
			String carId = car.getId();
			if (carId == null || carId.equals("")) {
				System.out.println("======>车排号为空");
				return false;
			}

			session = sessionFactory.openSession();
			session.beginTransaction();

			// 查询库
			String hql = "from Car c where c.id = ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, carId);
			Car oldCar = (Car) query.uniqueResult();
			if (oldCar == null) {
				System.out.println("======>数据库中查询没有此车");
				return false;
			}
			// 判断是否改变User
			if(car.getUser() != null) {
				if(car.getUser().getId() != null &&!car.getUser().getId().equals("")) {
					CarUser newUser = (CarUser) session.get(CarUser.class, car.getUser().getId());
					car.setUser(newUser);
				} else {
					car.setUser(null);
				}
			}
			// 更新carUser数据
			oldCar.update(car);
			System.out.println("更新数据");
			session.getTransaction().commit();

		} catch (TransientObjectException ex) {
			System.out.println("===============>发生异常，添加失败");
			return false;
		} finally {
			if (session != null)
				session.close();
		}
		return true;
	}

	@Override
	public int delete(List<Car> list) {
		Session session = sessionFactory.openSession();

		int successCount = 0;
		// 对集合中的User进行循环删除
		for (Car car : list) {
			session.beginTransaction();

			try {
				// 查询
				String hql = "from Car c where c.id = ?";
				Query query = session.createQuery(hql);
				query.setParameter(0, car.getId());
				Car tempCar = (Car) query.uniqueResult();
				if (tempCar == null) {
					// 表示表中并没有这个数据
					System.out.println("======>出现一条并不存在与数据库的数据,跳过");
					continue;
				}
				session.delete(tempCar);
			} catch (Exception e) {
				System.out.println("======>删除失败：" + car.getId());
				e.printStackTrace();
			}
			successCount++;
			session.getTransaction().commit();
		}
		return successCount;
	}

	@Override
	public boolean insert(Car c) {
		Session session = sessionFactory.openSession();

		// 获取到车牌号
		String carId = c.getId();
		// 获取到新车主身份证
		String userId = null;
		if (c.getUser() != null) {
			userId = c.getUser().getId();
			if (userId == null || userId.equals("")) {
				System.out.println("======>未填写车主");
				return false;
			}
		}
		try {
			session.beginTransaction();
			// 获取到车辆
			Car car = (Car) session.get(Car.class, carId);
			if (car != null) {
				System.out.println("======>当前车辆已经在数据库中");
				return false;
			}
			// 改变车辆的车主
			System.out.println("======>开始从数据库获取车主数据...");
			CarUser user = (CarUser) session.get(CarUser.class, userId);
			if (user == null) {
				System.out.println("======>车主没在数据库中");
				return false;
			}
			c.setUser(user);
			session.save(c);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[cDAO#insert]=====>insert出错");
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	@Override
	public List<Car> query(Car car) {
		Session session = sessionFactory.openSession();
		List<Car> resultList = new LinkedList();
		// 获取到车牌号，并添加条件
		String carId = car.getId();
		if (carId == null || carId.equals("")) {
			System.out.println("======>传入的car未告知车牌号");
			return resultList;
		}
		try {
			Car tempCar = (Car) session.get(Car.class, carId);
			if (tempCar != null) {
				resultList.add(tempCar);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>在查询时出现了异常");
			return resultList;
		} finally {
			session.close();
		}

	}

	@Override
	public List<Car> query(Car t, int start, int count, NULL o1, NULL o2) {
		Session session = sessionFactory.openSession();
		// 查询车主
		CarUser carUser = t.getUser();
		if (carUser != null) {
			String carUserId = carUser.getId();
			if(carUserId != null && !carUserId.equals(""))
				t.setUser((CarUser) session.get(CarUser.class, carUserId));
		}
		Criteria criteria = getCriteria(t, o1, o2, "", session);
		if (start != -1 && count != -1) {
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
		}
		try {
			return criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>查询出错");
		}
		return null;
	}

	@Override
	public long getDataCountWithLimit(Car t, NULL o1, NULL o2) {
		Session session = sessionFactory.openSession();

		Criteria criteria = getCriteria(t, o1, o2, "", session);
		criteria.setProjection(Projections.rowCount());
		try {
			String valStr = criteria.uniqueResult() + "";
			return Long.valueOf(valStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>根据条件查询数据数量出错");
			return -1;
		}
	}

	@Override
	protected void doBussiness(Car t, Criteria criteria) {
		if (t != null) {
			// 添加车牌限制条件
			String carId = t.getId();
			if (carId != null && !carId.equals("")) {
				criteria.add(Restrictions.eq("id", carId));
			}
			// 添加类型条件
			String carType = t.getCar_type();
			if (carType != null && !carType.equals("") && !carType.equals("全部")) {
				criteria.add(Restrictions.eq("car_type", carType));
			}
			// 添加车主限制
			CarUser user = t.getUser();
			if (user != null) {
				criteria.add(Restrictions.eq("user", user));
			}
			// 添加座位数限制
			String seatCount = t.getSeat_count();
			if (seatCount != null) {
				criteria.add(Restrictions.eq("seat_count", seatCount));
			}
			// 添加颜色限制
			String carColor = t.getCar_color();
			if (carColor != null) {
				criteria.add(Restrictions.eq("car_color", carColor));
			}
		}
	}

}

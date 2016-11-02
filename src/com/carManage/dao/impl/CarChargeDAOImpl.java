package com.carManage.dao.impl;

import java.util.Date;
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
import com.carManage.model.CarCharge;
import com.carManage.model.CarUser;
import com.carManage.model.User;

import edu.emory.mathcs.backport.java.util.LinkedList;

/**
 * 管理费页面 限制条件：月份，类型String
 * 
 * @author admin
 *
 */
@Repository("carChargeDAOImpl")
public class CarChargeDAOImpl extends BaseDAO<CarCharge, Integer> {

	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	@Resource(name = "userDAOImpl")
	BaseDAO<User, NULL> userDao;

	@Resource(name = "carDAOImpl")
	BaseDAO<Car, NULL> carDao;

	@Override
	public boolean update(CarCharge carCharge) {
		Session session = null;
		try {
			// 获取到收据号
			String id = carCharge.getRecript_id();
			if (id == null || id.equals("")) {
				System.out.println("======>收据号为空");
				return false;
			}

			session = sessionFactory.openSession();
			session.beginTransaction();

			// 查询库中是否有这个单子
			// String hql = "from CarCharge cc where cc.recript_id = ?";
			// Query query = session.createQuery(hql);
			// query.setParameter(0, id);
			// (CarCharge) query.uniqueResult();
			List<CarCharge> list = query(carCharge);
			if (list == null || list.size() == 0) {
				System.out.println("======>数据库中查询没有此收据");
				return false;
			}
			CarCharge temp = list.get(0);
			// 更新收据数据
			temp.update(carCharge);
			session.update(temp);
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
	public boolean insert(CarCharge t) {
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			// 判断收款人是否存在
			String gatherPerson = t.getGather_person();
			if (gatherPerson != null && !gatherPerson.equals("")) {
				User u = new User();
				// 通过用户名查询
				u.setName(gatherPerson);
				List<User> list = userDao.query(u);
				if (list == null || list.size() == 0) {
					System.out.println("并无此收款人");
					return false;
				}
			}

			// 判断车辆是否存在
			List<Car> list = carDao.query(t.getCar());
			if (list == null || list.size() == 0) {
				System.out.println("并无此车");
				return false;
			} else {
				Car car = list.get(0);
				t.setCar(car);
			}

			session.save(t);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>保存失败");
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	@Override
	public List<CarCharge> query(CarCharge t) {
		Session session = sessionFactory.openSession();
		List<CarCharge> resultList = null;
		try {
			CarCharge c = (CarCharge) session.get(CarCharge.class,
					t.getRecript_id());
			if (c != null) {
				resultList = new LinkedList();
				resultList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>查询CarCharge的时候出现错误");
		} finally {
			session.close();
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarCharge> query(CarCharge t, int start, int count, Integer o1,
			Integer o2) {
		List<CarCharge> resultList = new LinkedList();
		Session session = sessionFactory.openSession();
		Criteria criteria = getCriteria(t, o1, o2, "pay_month", session);
		// 添加分页条件
		criteria.setFirstResult(start);
		criteria.setMaxResults(count);
		try {
//			if (criteria.list() != null) {
//				resultList = criteria.list();
//			}
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			return resultList;
		} finally {
			session.close();
		}
	}

	@Override
	public long getDataCountWithLimit(CarCharge t, Integer o1, Integer o2) {
		Session session = sessionFactory.openSession();
		Criteria criteria = getCriteria(t, o1, o2, "pay_month", session);
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
	protected void doBussiness(CarCharge t, Criteria criteria) {

		if (t != null) {
			// 添加车牌查询条件
			Car car = t.getCar();
			if (car != null && car.getId() != null && !car.getId().equals("")) {
				// List<Car> list = carDao.query(car);
				// if(list != null || list.size() != 0) {
				// criteria.add(Restrictions.eq("car", list.get(0)));
				// }
				criteria.add(Restrictions.eq("car", car));
			}

			// 根据年份来
			String payYear = t.getPay_year();
			if (payYear != null && !payYear.equals("") && !payYear.equals("全部"))
				criteria.add(Restrictions.eq("pay_year", payYear));

			// 根据收据单
			String id = t.getRecript_id();
			if (id != null && !id.equals(""))
				criteria.add(Restrictions.eq("recript_id", id));

			// 缴费日期
			Date pay_time = t.getPay_time();
			if (pay_time != null)
				criteria.add(Restrictions.eq("pay_time", pay_time));
		}
	}

}

package com.carManage.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.model.Car;
import com.carManage.model.CarTransfer;
import com.carManage.model.CarUser;

/**
 * 车主变化 限制条件：转让日期，date类型
 * 
 * @author admin
 *
 */
@Repository("carTransferDAOImpl")
public class CarTransferDAOImpl extends BaseDAO<CarTransfer, Date> {
	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	/**
	 * 将数据插入数据库
	 */
	@Override
	public boolean insert(CarTransfer carTransfer) {
		Session session = sessionFactory.openSession();

		// 获取到车牌号
		String carId = carTransfer.getCarId();
		// 获取到新车主身份证
		String newUserId = carTransfer.getNew_user_id();
		// String oldUserId = carTransfer.getOld_user_id();

		try {
			session.beginTransaction();
			// 获取到车辆
			System.out.println("======>开始获取转换订单中的车辆...");
			Car car = (Car) session.get(Car.class, carId);
			if (car == null) {
				System.out.println("======>当前车辆没在数据库中");
				return false;
			}
			// 改变车辆的车主
			System.out.println("======>开始从数据库获取新车主对象...");
			CarUser newUser = (CarUser) session.get(CarUser.class, newUserId);
			if (newUser == null) {
				System.out.println("======>新车主没在数据库中");
				return false;
			}
			car.setUser(newUser);
//			 将改变后的车赋值给转换订单
//			carTransfer.setCar(car);

			session.save(carTransfer);
			session.getTransaction().commit();
			;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ctDAO#insert]=====>insert出错");
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	/**
	 * 可查询内容为：车牌号，原车主，现车主 如果传入的没有任何内容（传入实体中没有上述的数据），默认查询所有 返回值可能为null
	 */
	@Override
	public List<CarTransfer> query(CarTransfer carTransfer) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(CarTransfer.class);

		// 获取到车牌号，并添加条件
		if (carTransfer.getCarId() != null && !carTransfer.getCarId().equals("")) {
			String carId = carTransfer.getCarId();
			criteria.add(Restrictions.eq("carId", carId));
		}

		String newUserId = carTransfer.getNew_user_id();
		if (newUserId != null && !newUserId.equals("")
				&& !newUserId.equals("全部")) {
			criteria.add(Restrictions.eq("new_user_id", newUserId));
		}

		String oldUserId = carTransfer.getOld_user_id();
		if (oldUserId != null && !oldUserId.equals("")
				&& !oldUserId.equals("全部")) {
			criteria.add(Restrictions.eq("old_user_id", oldUserId));
		}

		try {
			List<CarTransfer> result = criteria.list();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>query中查询出错");
			return null;
		}
	}

	/**
	 * 带有附加条件以及分页请求参数的查询方法 carTransfer ： 如果传入null，表示查询整个表 start:数据库中开始的行数
	 * count：需要多少条数据 o1：第一个限制参数，查询的开始日期 o2：第二个限制参数，查询的结束日期
	 */
	@Override
	public List<CarTransfer> query(CarTransfer carTransfer, Integer start,
			Integer count, Date o1, Date o2) {
		if(start == null || count == null) {
			System.out.println("传入的start | count为空");
			return null;
		}
		Session session = sessionFactory.openSession();
		Criteria criteria = getCriteria(carTransfer, o1, o2, "transfer_time",
				session);

		// 处理分页请求
		if (start != -1 && count != -1) {
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
		}

		try {
			List<CarTransfer> result = criteria.list();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>query中查询出错");
			return null;
		}
	}

	@Override
	public long getDataCountWithLimit(CarTransfer carTransfer, Date o1, Date o2) {
		Session session = sessionFactory.openSession();
		Criteria criteria = getCriteria(carTransfer, o1, o2, "transfer_time",
				session);
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

	/**
	 * 
	 */
	@Override
	protected void doBussiness(CarTransfer carTransfer, Criteria criteria) {
		if (carTransfer != null) {
			// 获取到车牌号，并添加条件
			if (carTransfer.getCarId() != null && !carTransfer.getCarId().equals("")) {
				String carId = carTransfer.getCarId();
				Car car = new Car();
				car.setId(carId);
				criteria.add(Restrictions.eq("car", car));
			}

			// 新车主条件
			String newUserId = carTransfer.getNew_user_id();
			if (newUserId != null && !newUserId.equals("")
					&& !newUserId.equals("全部")) {
				criteria.add(Restrictions.eq("new_user_id", newUserId));
			}

			// 原车主条件
			String oldUserId = carTransfer.getOld_user_id();
			if (oldUserId != null && !oldUserId.equals("")
					&& !oldUserId.equals("全部")) {
				criteria.add(Restrictions.eq("old_user_id", oldUserId));
			}
		}
	}
}

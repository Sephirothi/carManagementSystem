package com.carManage.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.model.Car;
import com.carManage.model.CarTransfer;
import com.carManage.model.CarUser;

/**
 * 车主变化
 * 限制条件：转让日期，date类型
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
		String carId = carTransfer.getCar().getId();
		// 获取到新车主身份证
		String newUserId = carTransfer.getNew_user_id();
//		String oldUserId = carTransfer.getOld_user_id();
		
		try {
			session.beginTransaction();
			// 获取到车辆
			System.out.println("======>开始获取转换订单中的车辆...");
			Car car = (Car) session.get(Car.class, carId);
			if(car == null ) {
				System.out.println("======>当前车辆没在数据库中");
				return false;
			}
			// 改变车辆的车主
			System.out.println("======>开始从数据库获取新车主对象...");
			CarUser newUser = (CarUser) session.get(CarUser.class, newUserId);
			if(newUser == null) {
				System.out.println("======>新车主没在数据库中");
				return false;
			}
			car.setUser(newUser);
			// 将改变后的车赋值给转换订单
			carTransfer.setCar(car);
			
			session.save(carTransfer);
			session.getTransaction().commit();;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[ctDAO#insert]=====>insert出错");
			return false;
		} finally {
			session.close();
		}
		
		return true;
	}

	
	/**
	 * 可查询内容为：车牌号，原车主，现车主
	 * 如果传入的没有任何内容（传入实体中没有上述的数据），默认查询所有
	 * 返回值可能为null
	 */
	@Override
	public List<CarTransfer> query(CarTransfer carTransfer) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(CarTransfer.class);
		
		// 获取到车牌号，并添加条件
		if(carTransfer.getCar() != null) {
			String carId = carTransfer.getCar().getId();
			Car car = new Car();
			car.setId(carId);
			criteria.add(Restrictions.eq("car", car));
		}
		
		String newUserId = carTransfer.getNew_user_id();
		if( newUserId != null && !newUserId.equals("") && !newUserId.equals("全部")) {
			criteria.add(Restrictions.eq("new_user_id", newUserId));
		}
		
		String oldUserId = carTransfer.getOld_user_id();
		if( oldUserId != null && !oldUserId.equals("") && !oldUserId.equals("全部")) {
			criteria.add(Restrictions.eq("old_user_id", oldUserId));
		}
		
		try {
			List<CarTransfer> result = criteria.list();
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("======>query中查询出错");
			return null;
		}
	}

	@Override
	public List<CarTransfer> query(CarTransfer carTransfer, int start, int count,
			Date o1, Date o2) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(CarTransfer.class);
		
		// 获取到车牌号，并添加条件
		if(carTransfer.getCar() != null) {
			String carId = carTransfer.getCar().getId();
			Car car = new Car();
			car.setId(carId);
			criteria.add(Restrictions.eq("car", car));
		}
		
		// 新车主条件
		String newUserId = carTransfer.getNew_user_id();
		if( newUserId != null && !newUserId.equals("") && !newUserId.equals("全部")) {
			criteria.add(Restrictions.eq("new_user_id", newUserId));
		}
		
		// 原车主条件
		String oldUserId = carTransfer.getOld_user_id();
		if( oldUserId != null && !oldUserId.equals("") && !oldUserId.equals("全部")) {
			criteria.add(Restrictions.eq("old_user_id", oldUserId));
		}
		
		// 附加限制条件
		if(o1 != null) {
			
		}
		
		try {
			List<CarTransfer> result = criteria.list();
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("======>query中查询出错");
			return null;
		}
	}

	@Override
	public long getDataCount(CarTransfer t) {
		return super.getDataCount(t);
	}
}

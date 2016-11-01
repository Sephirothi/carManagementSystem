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
import com.carManage.model.Car;
import com.carManage.model.CarCharge;
import com.carManage.model.CarUser;

import edu.emory.mathcs.backport.java.util.LinkedList;

/**
 * 管理费页面 限制条件：月份，类型String （未测试）
 * 
 * @author admin
 *
 */
@Repository("carChargeDAOImpl")
public class CarChargeDAOImpl extends BaseDAO<CarCharge, String> {

	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

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

			// 查询库中是否有这个用户
			String hql = "from CarCharge cc where cc.recript_id = ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, id);
			CarCharge tempCC = (CarCharge) query.uniqueResult();
			if (tempCC == null) {
				System.out.println("======>数据库中查询没有此收据");
				return false;
			}
			// 更新收据数据
			tempCC.update(carCharge);
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

	@Override
	public List<CarCharge> query(CarCharge t, int start, int count, String o1,
			String o2) {
		List<CarCharge> resultList = new LinkedList();
		Session session = sessionFactory.openSession();
		Criteria criteria = getCriteria(t, o1, o2, "pay_month", session);
		// 添加分页条件
		criteria.setMaxResults(count);
		criteria.setFirstResult(start);
		try {
			if (criteria.list() != null) {
				resultList = criteria.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return resultList;
		} finally {
			session.close();
		}
		return resultList;

	}

	@Override
	public long getDataCountWithLimit(CarCharge t, String o1, String o2) {
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
			if (car != null)
				criteria.add(Restrictions.eq("car", car));

			// 根据年份来
			String payYear = t.getPay_year();
			if (payYear != null && !payYear.equals(""))
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

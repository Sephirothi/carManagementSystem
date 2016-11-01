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
import com.carManage.model.CarUser;
import com.carManage.model.User;

import edu.emory.mathcs.backport.java.util.LinkedList;

/**
 * 车主 限制条件：生日，date类型
 * 
 * @author admin
 *
 */
@Repository("carUserDAOImpl")
public class CarUserDAOImpl extends BaseDAO<CarUser, Date> {
	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	@Override
	public boolean update(CarUser cu) {
		Session session = null;
		try {
			// 获取到车主的身份证
			String userId = cu.getId();
			if (userId == null || userId.equals("")) {
				System.out.println("======>车主身份证为空");
				return false;
			}

			session = sessionFactory.openSession();
			session.beginTransaction();

			// 查询库中是否有这个用户
			String hql = "from CarUser cu where cu.id = ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, userId);
			CarUser carUser = (CarUser) query.uniqueResult();
			if (carUser == null) {
				System.out.println("======>数据库中查询没有此车主");
				return false;
			}
			// 更新carUser数据
			carUser.updateCarUser(cu);
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

	/**
	 * 进行删除 可以删除多个也可以删除单个 返回值为成功删除的个数
	 */
	@Override
	public int delete(List<CarUser> list) {
		Session session = sessionFactory.openSession();

		int successCount = 0;
		// 对集合中的User进行循环删除
		for (CarUser carUser : list) {
			session.beginTransaction();

			try {
				// 查询
				String hql = "from CarUser cu where cu.id = ?";
				Query query = session.createQuery(hql);
				query.setParameter(0, carUser.getId());
				CarUser tempUser = (CarUser) query.uniqueResult();
				if (tempUser == null) {
					// 表示表中并没有这个数据
					System.out.println("======>出现一条并不存在与数据库的数据,跳过");
					continue;
				}
				session.delete(tempUser);
			} catch (Exception e) {
				System.out.println("======>删除失败：" + carUser.getName());
				e.printStackTrace();
			}
			successCount++;
			session.getTransaction().commit();
		}
		return successCount;
	}

	/**
	 * 插入一个新的数据
	 */
	@Override
	public boolean insert(CarUser cu) {
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.save(cu);
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

	/**
	 * 进行单个查询，传入的t中包含有id
	 */
	@Override
	public List<CarUser> query(CarUser t) {
		Session session = sessionFactory.openSession();
		List<CarUser> resultList = null;
		try {
			CarUser r = (CarUser) session.get(CarUser.class, t.getId());
			if (r != null) {
				resultList = new LinkedList();
				resultList.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("======>查询CarUser的时候出现错误");
		} finally {
			session.close();
		}
		return resultList;
	}

	/**
	 * 查询多个，分页查询的时候对应的方法 不会返回null
	 */
	@Override
	public List<CarUser> query(CarUser cu, int start, int count, Date o1,
			Date o2) {
		List<CarUser> resultList = new LinkedList();
		Session session = sessionFactory.openSession();
		Criteria criteria = getCriteria(cu, o1, o2, "birthday", session);
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
	public long getDataCountWithLimit(CarUser t, Date o1, Date o2) {
		Session session = sessionFactory.openSession();
		Criteria criteria = getCriteria(t, o1, o2, "birthday", session);
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
	protected void doBussiness(CarUser cu, Criteria criteria) {
		if (cu != null) {
			// 添加姓名查询条件
			String name = cu.getName();
			if (name != null && !name.equals(""))
				criteria.add(Restrictions.eq("name", cu.getName()));

			// 添加性别查询条件
			String sex = cu.getSex();
			if (sex != null && !sex.equals("全部") && !sex.equals(""))
				criteria.add(Restrictions.eq("sex", cu.getSex()));

			// 添加电话查询条件
			String phone = cu.getPhone();
			if (phone != null && !phone.equals("")) {
				criteria.add(Restrictions.eq("phone", phone));
			}
		}
	}
}

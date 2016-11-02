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
import com.carManage.model.User;

/**
 * 对用户表的增删改查 限制条件：无
 * 
 * @author admin
 *
 */
@Repository("userDAOImpl")
public class UserDAOImpl extends BaseDAO<User, NULL> {
	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	/**
	 * 进行User信息的更改 通过Username来进行区分
	 */
	@Override
	public boolean update(User t) {
		Session session = null;
		try {
			String username = t.getUsername();
			if (username == null || username.equals("")) {
				System.out.println("[UserDaoImpl#update]======>username为空");
				return false;
			}

			session = sessionFactory.openSession();
			session.beginTransaction();

			// 查询库中是否有这个用户
			List<User> tList = query(t);
			if(tList == null || tList.size() == 0) {
				System.out.println("======>数据库中查询没有此用户");
				return false;
			}
			User user = tList.get(0);
//			String hql = "from User u where u.username = ?";
//			Query query = session.createQuery(hql);
//			query.setParameter(0, t.getUsername());
//			User user = (User) query.uniqueResult();
//			if (user == null) {
//				System.out.println("======>数据库中查询没有此用户");
//				return false;
//			}
			// 更新User数据
			user.updateUser(t);
			session.update(user);
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
	public int delete(List<User> list) {
		Session session = sessionFactory.openSession();

		int successCount = 0;
		// 对集合中的User进行循环删除
		for (User user : list) {
			session.beginTransaction();

			try {
				// 查询
//				String hql = "from User u where u.username = ?";
//				Query query = session.createQuery(hql);
//				query.setParameter(0, user.getUsername());
//				User tempUser = (User) query.uniqueResult();
				List<User> tList = query(user);
				if (tList == null || tList.size() == 0) {
					// 表示表中并没有这个数据
					continue;
				}
				User tempUser = tList.get(0);
				session.delete(tempUser);
			} catch (Exception e) {
				System.out.println("======>删除失败：" + user.getUsername());
				e.printStackTrace();
			}
			successCount++;
			session.getTransaction().commit();
		}
		// 关闭资源
		session.close();
		return successCount;
	}

	/**
	 * 添加一个数据到数据库中
	 */
	@Override
	public boolean insert(User t) {
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

	/**
	 * 查询：根据传递的数据来确定是查询单个还是多个 如果带有登录编号username，则表示查询单个 如果没有登录编号，则表示查询多个
	 *
	 */
	@Override
	public List<User> query(User t) {
		if(t == null) {
			System.out.println("[UserDao#query]=====>传入的User对象为null");
			return null;
		}
		Session session = sessionFactory.openSession();
//		Criteria criteria = session.createCriteria(User.class);
		List<User> resultList = null;
		// 判断是否有UserName
		if (t.getUsername() != null && !t.getUsername().equals("")) {
			// // 此时表示查询多个
			// if (t.getName() != null && !t.getName().equals(""))
			// criteria.add(Restrictions.eq("name", t.getName()));
			// if (t.getState() != null && !t.getState().equals("全部"))
			// criteria.add(Restrictions.eq("state", t.getState()));
			// } else {
			// 此时表示查询单个
			// 查询
			String hql = "from User u where u.username = ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, t.getUsername());
			try {
				resultList = query.list();
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("[UserDao#query]======>查询出错");
			} finally {
				session.close();
			}
		} else {
			System.out.println("[UserDao#query]======>没有Username");
		}
		return resultList;
	}

	/**
	 * 进行分页查询 正常返回固定条数的集合 如果出现错误，或者是参数有误，返回值为null
	 */
	@Override
	public List<User> query(User t, Integer start, Integer count, NULL o1,
			NULL o2) {
		if (start == null || count == null) {
			System.out.println("======>传入的start | count为空");
			return null;
		}
		if (t == null)
			return null;
		Session session = sessionFactory.openSession();
		Criteria criteria = getCriteria(t, o1, o2, "", session);
		// 设置分页
		criteria.setFirstResult(start);
		criteria.setMaxResults(count);
		return criteria.list();
	}

	@Override
	protected void doBussiness(User t, Criteria criteria) {
		if (t != null) {
			// 此时表示查询多个
			if (t.getName() != null && !t.getName().equals(""))
				criteria.add(Restrictions.eq("name", t.getName()));
			if (t.getState() != null && !t.getState().equals("全部"))
				criteria.add(Restrictions.eq("state", t.getState()));
		}
	}

	@Override
	public long getDataCountWithLimit(User t,
			com.carManage.dao.BaseDAO.NULL o1, com.carManage.dao.BaseDAO.NULL o2) {
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

}

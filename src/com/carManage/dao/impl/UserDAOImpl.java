package com.carManage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransientObjectException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.model.User;

/**
 * 对用户表的增删改查
 * 
 * @author 47
 *
 */
@Repository("userDAOImpl")
public class UserDAOImpl extends BaseDAO<User> {
	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	
	/**
	 * 进行User信息的更改
	 */
	@Override
	public boolean update(User t) {
		Session session = null;
		try {
			String username = t.getUsername();
			if (username == null || username.equals("")) {
				System.out.println("======>username为空");
				return false;
			}
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			// 查询库中是否有这个用户
			String hql = "from User u where u.username = ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, t.getUsername());
			User user = (User) query.uniqueResult();
			if (user == null) {
				System.out.println("======>数据库中查询没有此用户");
				return false;
			}
			// 更新User数据
			user.updateUser(t);
			
			session.getTransaction().commit();
			
		} catch (TransientObjectException ex) {
			System.out.println("===============>发生异常，添加失败");
			return false;
		} finally {
			if(session != null)
				session.close();
		}
		return true;
	}

	@Override
	public int delete(List<User> list) {
		Session session = sessionFactory.openSession();

		int successCount = 0;
		// 对集合中的User进行循环删除
		for(User user : list) {
			session.beginTransaction();
			
			try {
				// 查询
				String hql = "from User u where u.username = ?";
				Query query = session.createQuery(hql);
				query.setParameter(0, user.getUsername());
				User tempUser = (User) query.uniqueResult();
				if(tempUser == null) {
					
				}
			} catch(Exception e) {
				System.out.println("======>删除失败：" + user.getUsername());
				e.printStackTrace();
			}
			
			session.getTransaction().commit();
		}
		return 0;
	}

	@Override
	public boolean insert(User t) {
		// TODO Auto-generated method stub
		return super.insert(t);
	}

	/**
	 * 查询：根据传递的数据来确定是查询单个还是多个 如果带有登录编号username，则表示查询单个 如果没有登录编号，则表示查询多个
	 *
	 */
	@Override
	public List<User> query(User t) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		List<User> resultList = null;
		if (t.getUsername().equals("")) {
			// 此时表示查询多个
			if (!t.getName().equals(""))
				criteria.add(Restrictions.eq("name", t.getName()));
			if (!t.getState().equals("全部"))
				criteria.add(Restrictions.eq("state", t.getState()));
		} else {
			// 此时表示查询单个
			criteria.add(Restrictions.eq("username", t.getUsername()));
		}
		resultList = criteria.list();

		session.close();
		return resultList;
	}

}

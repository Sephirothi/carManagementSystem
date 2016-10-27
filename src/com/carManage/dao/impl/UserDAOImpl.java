package com.carManage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.carManage.dao.UserDao;
import com.carManage.model.User;
/**
 * 对用户表的增删改查
 * @author 47
 *
 */
@Repository("userDAOImpl")
public class UserDAOImpl implements UserDao<User> {
	@Resource(name="sessionFactory")
	SessionFactory sessionFactory;
	@Override
	public boolean insertUser(User t) {
		//TODO
		return false;
	}

	@Override
	public boolean deleteUser(User t) {
		//TODO
		return false;
	}

	@Override
	public boolean updateUser(User t) {
		//TODO
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryUser(User t) {
		Session  session=sessionFactory.openSession();
		String qs="from User u where u.username=? and u.password=?";
		Query q=session.createQuery(qs);
		q.setParameter(0, t.getUsername());
		q.setParameter(1, t.getPassword());
		return  q.list();
	}

	

}

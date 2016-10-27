package com.carManage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.carManage.dao.UserDao;
import com.carManage.model.User;
import com.carManage.service.UserService;
@Repository("userServiceImpl")
public class UserServiceImpl implements UserService<User> {
	@Resource(name="userDAOImpl")
	private UserDao userdao;
	@Override
	public boolean insertUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> queryAll(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User querySingle(User u) {
		List<User> ulist= userdao.queryUser(u);
		if(ulist.size()==1){
			return ulist.get(0);
		}
		return null;
	}

}

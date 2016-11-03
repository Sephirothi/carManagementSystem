package com.carManage.service;

import com.carManage.model.User;

public interface UserService {
	String insertUser(String json);
	String deleteUser(String json);
	String updateUser(String json);
	String queryAllUsers(String json);
	String querySingleUser(String json);
	User checklogin(String json);
}
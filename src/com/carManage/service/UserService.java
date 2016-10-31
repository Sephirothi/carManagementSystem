package com.carManage.service;

public interface UserService {
	String insertUser(String json);
	String deleteUser(String json);
	String updateUser(String json);
	String queryAllUsers(String json);
	String querySingleUser(String json);
	String checklogin(String json);
}

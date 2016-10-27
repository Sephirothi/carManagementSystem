package com.carManage.service;

import java.util.List;

public interface UserService<T> {
	boolean insertUser(T u);
	boolean deleteUser(T u);
	boolean updateUser(T u);
	List<T> queryAll(T u);
	T querySingle(T u);
}

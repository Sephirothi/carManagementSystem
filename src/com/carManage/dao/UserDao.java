package com.carManage.dao;

import java.util.List;
/**
 * 增删改查的基础接口
 * @author hp
 *
 * @param <T>
 */
public interface UserDao<T> {
	boolean insertUser(T t);

	boolean deleteUser(T t);

	boolean updateUser(T t);

	List<T> queryUser(T t);
}

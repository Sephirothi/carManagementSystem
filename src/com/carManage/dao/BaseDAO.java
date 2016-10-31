package com.carManage.dao;

import java.util.List;

public abstract class BaseDAO<T, E> implements IDAO<T> {

	@Override
	public boolean update(T t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int delete(List<T> t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean insert(T t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<T> query(T t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param t
	 *            传入的表实体
	 * @param start
	 *            请求数据，开始的位置
	 * @param count
	 *            请求的条数
	 * @param o1
	 *            限制条件1
	 * @param o2
	 *            限制条件2
	 * @return
	 */
	public List<T> query(T t, int start, int count, E o1, E o2) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取到数据库中此类对象的总的条数
	 * @param t 需要查询的对象
	 * @return
	 */
	public long getDataCount(T t) {
		try {
			addException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private void addException() throws Exception {
		throw new Exception("no this method");
	}
	
	public interface NULL {}
}

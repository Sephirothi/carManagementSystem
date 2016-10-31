package com.carManage.dao;

import java.util.List;

public abstract class BaseDAO<T> implements IDAO<T>{

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
	
	private void addException() throws Exception{
		throw new Exception("no this method");
	}
}

package com.carManage.service;

public interface CarUserService {
	String queryCarUsers(String json);
	String deleteCarUsers(String json);
	String queryCarUser(String json);
	String updateCar(String json);
	String insert(String json);
}

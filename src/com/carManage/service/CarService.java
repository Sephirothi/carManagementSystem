package com.carManage.service;

public interface CarService {
	String queryCars(String json);
	String deleteCars(String json);
	String queryCar(String json);
	String updateCar(String json);
}

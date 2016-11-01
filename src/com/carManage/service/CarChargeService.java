package com.carManage.service;

public interface CarChargeService {
	String queryCarCharges(String json);
	String queryCarCharge(String json);
	String updateCarCharge(String json);
	String insertCarCharge(String json);
}

package com.carManage.service;

public interface CarTransferService {
	
	String queryCarTransfers(String json);
	String addCarTransfers(String json);
	String querySingle(String json);
}

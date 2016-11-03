package com.carManage.web.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.carManage.service.CarChargeService;
import com.opensymphony.xwork2.ActionSupport;

public class CarChargeAction extends ActionSupport {

	private static final long serialVersionUID = -4738601049396076169L;

	@Resource(name = "carChargeServiceImpl")
	private CarChargeService ccs;

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/*
	 * String queryCarCharges(String json); String queryCarCharge(String json);
	 * String updateCarCharge(String json); String insertCarCharge(String json);
	 */

	public String querys() {

		String json = ccs.queryCarCharges(data);
		inputStream = new ByteArrayInputStream(json.getBytes());
		return SUCCESS;
	}

	public String query() {
		String json = ccs.queryCarCharge(data);
		inputStream = new ByteArrayInputStream(json.getBytes());
		return SUCCESS;
	}

	public String update() {
		String json = ccs.updateCarCharge(data);
		inputStream = new ByteArrayInputStream(json.getBytes());
		return SUCCESS;
	}

	public String insert() {
		String json = ccs.insertCarCharge(data);
		inputStream = new ByteArrayInputStream(json.getBytes());
		return SUCCESS;
	}
}

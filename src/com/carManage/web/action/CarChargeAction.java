package com.carManage.web.action;

import java.io.IOException;

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

	/*
	 * String queryCarCharges(String json); String queryCarCharge(String json);
	 * String updateCarCharge(String json); String insertCarCharge(String json);
	 */

	public String querys() {
		String result = ccs.queryCarCharges(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String query() {
		String result = ccs.queryCarCharge(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String update() {
		String result = ccs.updateCarCharge(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String insert() {
		String result = ccs.insertCarCharge(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}
}

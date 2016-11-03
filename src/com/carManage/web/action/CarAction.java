package com.carManage.web.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.carManage.service.CarService;
import com.opensymphony.xwork2.ActionSupport;

public class CarAction extends ActionSupport {

	private static final long serialVersionUID = 520871950005534751L;

	@Resource(name = "carServiceImpl")
	private CarService cs;
	
	private String data;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	/**
	 * String queryCars(String json);
	String deleteCars(String json);
	String queryCar(String json);
	String updateCar(String json);
	String addCar(String json);
	 */
	
	public String querys() {
		String result = cs.queryCars(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}
	public String delete() {
		String result = cs.deleteCars(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}
	public String query() {
		String result = cs.queryCar(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}
	public String update() {
		String result = cs.updateCar(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}
	public String insert() {
		String result = cs.addCar(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR;
		}
		return "";
	}
}

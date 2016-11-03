package com.carManage.web.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.struts2.ServletActionContext;

import com.carManage.service.CarUserService;
import com.opensymphony.xwork2.ActionSupport;

public class CarUserAction extends ActionSupport {

	private static final long serialVersionUID = -1132422291795319913L;

	@Resource(name = "carUserServiceImpl")
	private CarUserService cus;

	private String data;

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	/*
	 * String queryCarUsers(String json); String deleteCarUsers(String json);
	 * String queryCarUser(String json); String updateCar(String json); String
	 * insert(String json);
	 */

	public String querys() {
		String result = cus.queryCarUsers(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String delete() {
		String result = cus.deleteCarUsers(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String query() {
		String result = cus.queryCarUser(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String update() {
		String result = cus.updateCar(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String insert() {
		String result = cus.insert(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

}

package com.carManage.web.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.struts2.ServletActionContext;

import com.carManage.service.CarUserService;
import com.carManage.utils.GsonUtils;
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

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/*
	 * String queryCarUsers(String json); String deleteCarUsers(String json);
	 * String queryCarUser(String json); String updateCar(String json); String
	 * insert(String json);
	 */

	public String querys() {
		System.out.println(data);
		String json = cus.queryCarUsers(data);
		System.out.println(json);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String delete() {
		System.out.println(data);
		String json = cus.deleteCarUsers(data);
		System.out.println(json);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String query() {
		String json = cus.queryCarUser(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String update() {
		System.out.println(data);
		String json = cus.updateCar(data);
		System.out.println(json);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String insert() {
		String json = cus.insert(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

}

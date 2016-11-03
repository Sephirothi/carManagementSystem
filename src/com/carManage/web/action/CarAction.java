package com.carManage.web.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.carManage.service.CarService;
import com.carManage.utils.GsonUtils;
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

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * String queryCars(String json); String deleteCars(String json); String
	 * queryCar(String json); String updateCar(String json); String
	 * addCar(String json);
	 */

	public String querys() {
		
		String json = cs.queryCars(data);
		System.out.println(data);
		System.out.println(json);
		try {
			inputStream = new ByteArrayInputStream(json.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String delete() {
		String json = cs.deleteCars(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String query() {
		String json = cs.queryCar(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String update() {

		String json = cs.updateCar(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String insert() {
		String json = cs.addCar(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}
}

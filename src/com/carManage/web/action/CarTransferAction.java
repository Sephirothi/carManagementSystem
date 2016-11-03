package com.carManage.web.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.carManage.service.CarTransferService;
import com.carManage.utils.GsonUtils;
import com.opensymphony.xwork2.ActionSupport;

public class CarTransferAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9052715343974246270L;

	@Resource(name = "carTransferServiceImpl")
	private CarTransferService ctfs;

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
	 * String queryCarTransfers(String json); String addCarTransfers(String
	 * json); String querySingle(String json);
	 */

	public String add() {
		String json = ctfs.addCarTransfers(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String query() {
		String json = ctfs.querySingle(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String querys() {
		String json = ctfs.queryCarTransfers(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}
}

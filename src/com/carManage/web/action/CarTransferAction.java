package com.carManage.web.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.carManage.service.CarTransferService;
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


	/*
	 * String queryCarTransfers(String json); String addCarTransfers(String
	 * json); String querySingle(String json);
	 */

	public String add() {
		String result = ctfs.addCarTransfers(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String query() {
		String result = ctfs.querySingle(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}

	public String querys() {
		String result = ctfs.queryCarTransfers(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(result);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "";
	}
}

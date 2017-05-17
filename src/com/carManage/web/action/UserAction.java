package com.carManage.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import com.carManage.model.User;
import com.carManage.service.UserService;
import com.carManage.utils.GsonUtils;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 571641429426477845L;
	@Resource(name = "userServiceImpl")
	private UserService us;

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

	public String checklogin() {
		System.out.println(data);
		User u = us.checklogin(data);
		if (u == null) {
			System.out.println("====>error");
			return ERROR;
		}
		try {
			String json = GsonUtils.objectToJson(u);
			inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
			System.out.println(json);
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		System.out.println("====>success");
		return SUCCESS;
	}

	 public String login() {
	 System.out.println(data);
	 String json = us.querySingleUser(data);
	 System.out.println(json);
	 inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
	 return SUCCESS;
	 }

	public String insert() {
		System.out.println(data);
		String json = us.insertUser(data);
		System.out.println(json);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String querys() {
		System.out.println(data);
		String json = us.queryAllUsers(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		System.out.println(json);
		return SUCCESS;
	}

	public String query() {
		System.out.println(data);
		String json = us.querySingleUser(data);
		System.out.println(json);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

	public String update() {
		System.out.println(data);
		String json = us.updateUser(data);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}
	
	public String delete() {
		System.out.println(data);
		String json = us.deleteUser(data);
		System.out.println(json);
		inputStream = new ByteArrayInputStream(GsonUtils.getJsonByte(json));
		return SUCCESS;
	}

}

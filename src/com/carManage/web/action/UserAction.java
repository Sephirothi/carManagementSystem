package com.carManage.web.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.carManage.model.User;
import com.carManage.service.UserService;
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

	/*
	 * String insertUser(String json); String deleteUser(String json); String
	 * updateUser(String json); String queryAllUsers(String json); String
	 * querySingleUser(String json); User checklogin(String json);
	 */

	public String login() {
		System.out.println(data);
		User u = us.checklogin(data);
		if (u == null) {
			return "error";
		}
		ServletActionContext.getRequest().getSession().setAttribute("user", u.getName());
		return "success";
	}

	public String insert() {
		String json = us.insertUser(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(json);
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR;
		}
		return "";
	}

	public String querys() {
		String json = us.queryAllUsers(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(json);
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR;
		}
		return "";
	}

	public String query() {
		String json = us.querySingleUser(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(json);
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR;
		}
		return "";
	}

	public String update() {
		String json = us.updateUser(data);
		try {
			ServletActionContext.getResponse().getOutputStream().print(json);
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR;
		}
		return "";
	}

}

package com.carManage.web.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;

import org.apache.struts2.ServletActionContext;

import com.carManage.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 571641429426477845L;
	@Resource(name = "userServiceImpl")
	private UserService us;
	
	private String data ;
	public void setData(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	
	/*
	 * 	String insertUser(String json);
	String deleteUser(String json);
	String updateUser(String json);
	String queryAllUsers(String json);
	String querySingleUser(String json);
	String checklogin(String json);
	 */

	public String login() {
		String result = us.querySingleUser(data);
		
		return "success";
	}
}

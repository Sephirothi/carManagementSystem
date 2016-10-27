package com.carManage.web.action;

import javax.annotation.Resource;

import com.carManage.model.User;
import com.carManage.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 571641429426477845L;
	@Resource(name = "userServiceImpl")
	private UserService us;
	private User user;

	public User getUser() {
		return user;
	}
	
	
	public void setUser(User user) {
		this.user = user;
	}

	public String login() {
		System.out.println(getUser().getUsername());
		User u = (User) us.querySingle(getUser());
		if (u != null) {
			ActionContext.getContext().getSession().put("u", u);
			return SUCCESS;
		} else
			return ERROR;
	}

	

}

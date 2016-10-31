package com.carManage.service.impl;

import java.util.List;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.carManage.dao.BaseDAO;
import com.carManage.model.User;
import com.carManage.service.ResponseType;
import com.carManage.service.UserService;
import com.carMmanage.utils.GsonUtils;
/**
 * 1 success
 * 0 failure
 * @author hp
 *
 */
@Service("userServiceImpl")
public class UserServiceImpl extends ResponseType implements UserService {
	@Resource(name = "userDAOImpl")
	private BaseDAO<User> baseDao;

	@Override
	public String insertUser(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUser(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUser(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUsers(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAllUsers(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String querySingleUser(String json) {
		String result = null;
		// 返回json包装类 
		ResponseBody<User> rb = new ResponseBody<>();
		try {
			// 将前端传过来的数据转化为
			List<User> user = GsonUtils.jsonToList(json, User.class);
			// List<User> list = null;
			if(user.size()==1){
				List<User> list  = baseDao.query(user.get(0));
				if(list.size()!=1){
					result=extracted(0,"传输数据有误");
				}else{
					rb.code=1;
					rb.data = list.get(0);
					result = GsonUtils.objectToJson(rb);
				}
			}else{
				result=extracted(0,"数据不是唯一性");
			}
		} catch (DataFormatException e) {
			//转化失败返回的结果
			result = extracted(0,"json转化失败");
		}
		return result;
	}


	@Override
	public String checklogin(String json) {
		String result = null;
		// 返回json包装类
		ResponseBody<String> rb = new ResponseBody<>();
		try {
			// 将前端传过来的数据转化为
			List<User> user = GsonUtils.jsonToList(json, User.class);
			// List<User> list = null;
			if (user.size() == 1) {
				User u = user.get(0);
				if (u.getUsername().trim().equals("") || u.getPassword().trim().equals("")) {
					rb.code = 0;// 表示认证失败
					rb.data = "帐号或密码错误,请重试";
				} else {
					if (baseDao.query(u).size() == 1) {
						rb.code = 1;// 表示认证成功
						rb.data = "登录成功";
					} else {
						rb.code = 0;// 表示认证失败
						rb.data = "帐号或密码错误,请重试";
					}
				}
			} else {
				rb.code=0;
				rb.data="user不唯一";
			}
			result = GsonUtils.objectToJson(rb);
		} catch (DataFormatException e) {
			result = extracted(0,"json转化失败");
		}
		return result;
	}
	
}

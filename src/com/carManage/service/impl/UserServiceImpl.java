package com.carManage.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.carManage.dao.BaseDAO;
import com.carManage.dao.BaseDAO.NULL;
import com.carManage.model.User;
import com.carManage.service.ResponseType;
import com.carManage.service.UserService;
import com.carManage.utils.GsonUtils;

/**
 * 1 success 0 failure
 * 
 * @author 47
 *
 */
@Service("userServiceImpl")
public class UserServiceImpl extends ResponseType implements UserService {
	@Resource(name = "userDAOImpl")
	private BaseDAO<User, NULL> baseDao;

	@Override
	public String insertUser(String json) {
		String result = null;
		ReturnResponse<String> rr = new ReturnResponse<>();
		try {
			List<User> list = GsonUtils.jsonToList(json, User.class);
			if (list.size() != 1) {
				// 其实msg可以拿出来==!
				result = rr.extracted(0, "传送的数据有误");
			} else {
				if (baseDao.insert(list.get(0))) {
					result = rr.extracted(1, "添加数据成功");
				} else {
					result = rr.extracted(0, "添加数据失败");
				}
			}
		} catch (DataFormatException e) {
			// 转化失败返回的结果
			result = rr.extracted(0, "json转化失败");
		}
		return result;
	}

	@Override
	public String deleteUser(String json) {
		String result = null;
		// 返回类型
		ReturnResponse<String> rr = new ReturnResponse<>();
		try {
			List<User> list = GsonUtils.jsonToList(json, User.class);
			if (list.size() == 1) {
				if (baseDao.delete(list) != 1) {
					result = rr.extracted(0, "删除数据失败");
				} else {
					result = rr.extracted(1, "删除数据成功");
				}

			} else {
				result = deleteUsers(list, rr);
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "Json转化失败");
		}
		return result;
	}

	private String deleteUsers(List<User> list, ReturnResponse<String> rr) {
		String result = null;
		if (list.size() == 0) {
			result = rr.extracted(0, "请选择要删除的对象！");
		} else {
			if (baseDao.delete(list) != list.size()) {
				result = rr.extracted(0, "删除数据失败");
			} else {
				result = rr.extracted(1, "删除数据成功");
			}

		}
		return result;
	}

	@Override
	public String updateUser(String json) {
		String result = null;
		// 返回类型
		ReturnResponse<String> rr = new ReturnResponse<>();
		try {
			List<User> list = GsonUtils.jsonToList(json, User.class);
			if (list.size() != 1) {
				// 其实msg可以拿出来==!
				result = rr.extracted(0, "传送的数据有误");
			} else {
				if (baseDao.update(list.get(0))) {
					result = rr.extracted(1, "修改数据成功");
				} else {
					result = rr.extracted(0, "修改数据失败");
				}
			}
		} catch (DataFormatException e) {
			// 转化失败返回的结果
			result = rr.extracted(0, "json转化失败");
		}
		return result;
	}

	@Override
	public String queryAllUsers(String json) {
		// TODO这个线不写,等待后台确定数据参数
		String result = null;
		ReturnResponse<List<User>> rr = new ReturnResponse<>();

		try {
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			User u = getMaptoObject(map, User.class);

			List<User> users = baseDao.query(u, stringToInteger(map.get("start")), stringToInteger(map.get("count")),
					null, null);
			if (users == null || users.size() == 0) {
				result = rr.extracted(0, "未查到符合的数据");
			} else {
				result = rr.extracted(1, users, baseDao.getDataCount(u));
			}
		} catch (InstantiationException e) {
			result = rr.extracted(0, "反射失败1");
		} catch (IllegalAccessException e) {
			result = rr.extracted(0, "反射失败2");
		} catch (IllegalArgumentException e) {
			result = rr.extracted(0, "反射失败3");
		} catch (InvocationTargetException e) {
			result = rr.extracted(0, "反射失败4");
		} catch (DataFormatException e) {
			rr.extracted(0, "JSON转化失败");
		}

		return result;
	}

	@Override
	public String querySingleUser(String json) {
		String result = null;
		// 返回json包装类
		ReturnResponse<User> rr = new ReturnResponse<>();
		try {
			// 将前端传过来的数据转化为
			List<User> user = GsonUtils.jsonToList(json, User.class);
			if (user.size() == 1) {
				User u = user.get(0);
				List<User> list = baseDao.query(u);
				if (list == null || list.size() != 1) {
					result = rr.extracted(0, "查询不到该用户，请检查用户名和密码！");
				} else {
					result = rr.extracted(1, list.get(0), null);
				}
			} else {
				// 如果数据不是唯一的,返回错误不需要查询数据库
				result = rr.extracted(0, "该用户名和密码存在多个映射！");
			}
		} catch (DataFormatException e) {
			// 转化失败返回的结果
			result = rr.extracted(0, "json转化失败");
		}
		return result;
	}

	@Override
	public User checklogin(String json) {
		String result = null;
		// 返回json包装类
		// ResponseBody<String> rb = new ResponseBody<>();
		// ReturnResponse<String> rr = new ReturnResponse<>();
		try {
			// 将前端传过来的数据转化为
			List<User> user = GsonUtils.jsonToList(json, User.class);
			// List<User> list = null;
			if (user.size() == 1) {
				User u = user.get(0);
				if (u.getUsername().trim().equals("") || u.getPassword().trim().equals("")) {
					return null;
				} else {
					// 查询数据库判断是否有这个人
					List<User> list = baseDao.query(u);
					if (list == null || list.size() != 1) {
						// rb.code = 1;// 表示认证成功
						// rb.data = "登录成功";
						return null;
					} else {
						return list.get(0);
					}
				}
			} else {
				// 数据不唯一,返回错误码
				// rb.code=0;
				// rb.data="user不唯一";
				return null;
			}
			// result = GsonUtils.objectToJson(rb);
		} catch (DataFormatException e) {
			return null;
		}

	}

}

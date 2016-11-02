package com.carManage.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.model.CarUser;
import com.carManage.service.CarUserService;
import com.carManage.service.ResponseType;
import com.carManage.utils.GsonUtils;

/**
 * 车主管理相关操作 条件查询传递json要求 starttime endtime
 * 
 * @author 47
 *
 */
@Repository("carUserServiceImpl")
public class CarUserServiceImpl extends ResponseType implements CarUserService {
	@Resource(name = "carUserDAOImpl")
	private BaseDAO<CarUser, Date> baseDao;

	/**
	 * json是单个对象 按条件查询所有车主信息
	 */
	@Override
	public String queryCarUsers(String json) {
		String result = null;
		ReturnResponse<List<CarUser>> rr = new ReturnResponse<>();
		try {
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarUser cu = getMaptoObject(map, CarUser.class);
			Date o1 = StringToDate(map.get("starttime"));
			Date o2 = StringToDate(map.get("endtime"));
			List<CarUser> list = baseDao.query(cu, stringToInteger(map.get("start")), stringToInteger(map.get("count")),
					o1, o2);
			result = (list == null) ? rr.extracted(0, "没有要查询的数据")
					: rr.extracted(1, list, baseDao.getDataCountWithLimit(cu, o1, o2));
		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化错误");
		} catch (Exception e) {
			result = rr.extracted(0, "反射失败");
		}
		return result;
	}

	@Override
	public String deleteCarUsers(String json) {
		String result = null;
		ReturnResponse<List<CarUser>> rr = new ReturnResponse<>();
		try {
			List<CarUser> list = GsonUtils.jsonToList(json, CarUser.class);
			if (list.size() == 0) {
				result = rr.extracted(0, "没有要删除的数据");
			} else {
				result = (baseDao.delete(list) == list.size()) ? rr.extracted(0, "删除成功") : rr.extracted(0, "删除失败");
			}
		} catch (DataFormatException e) {
			rr.extracted(0, "json转化失败");
		}
		return result;
	}

	@Override
	public String queryCarUser(String json) {
		String result = null;
		ReturnResponse<CarUser> rr = new ReturnResponse<>();
		try {
			List<CarUser> list = GsonUtils.jsonToList(json, CarUser.class);
			if (list.size() != 1) {
				result = rr.extracted(0, "没有数据过来");
			} else {
				List<CarUser> cu = baseDao.query(list.get(0));
				result = (cu == null || cu.size() != 1) ? rr.extracted(0, "传输数据有误")
						: rr.extracted(1, list.get(0), null);
			}

		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化错误");
		}
		return result;
	}

	@Override
	public String updateCar(String json) {
		String result = null;
		ReturnResponse<CarUser> rr = new ReturnResponse<>();
		try {
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarUser cu = getMaptoObject(map, CarUser.class);
			cu.setBirthday(StringToDate(map.get("birthday")));
			cu.setCreate_time(StringToDate(map.get("create_time")));
			result = baseDao.update(cu) ? rr.extracted(1, "更新成功") : rr.extracted(0, "更新失败");

		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化错误");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String insert(String json) {
		String result = null;
		ReturnResponse<CarUser> rr = new ReturnResponse<>();
		try {
			CarUser cu = GsonUtils.jsonToObject(json, CarUser.class);

			result = baseDao.insert(cu) ? rr.extracted(1, "添加成功") : rr.extracted(0, "添加失败");

		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化错误");
		}
		return result;
	}

}

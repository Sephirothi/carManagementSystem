package com.carManage.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.dao.BaseDAO.NULL;
import com.carManage.model.Car;
import com.carManage.model.CarUser;
import com.carManage.service.CarService;
import com.carManage.service.ResponseType;
import com.carManage.utils.GsonUtils;
/**
 * 如果传递车主信息身份,则用car_user
 * @author hp
 *
 */
@Repository("carServiceImpl")
public class CarServiceImpl extends ResponseType implements CarService {
	@Resource(name = "carDAOImpl")
	private BaseDAO<Car, NULL> baseDao;

	/**
	 * 按条件查询符合条件的数据
	 */
	@Override
	public String queryCars(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<List<Car>> rr = new ReturnResponse<>();
		try {
			// 按条件查询的json传输要求为一个对象,即{name:..}
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			Car car = getMaptoObject(map, Car.class);
			List<Car> list = baseDao.query(car, stringToInteger(map.get("start")), stringToInteger(map.get("count")),
					null, null);
			//System.out.println(list.size());
			if (list == null || list.size() == 0) {
				result = rr.extracted(0, "没有查到符合条件的数据");
			} else {
				result = rr.extracted(1, list, baseDao.getDataCount(car));
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化失败");
		} catch (Exception e) {
			e.printStackTrace();
			result = rr.extracted(0, "反射出问题");
		}
		return result;
	}

	/**
	 * 删除车辆信息,按主键,即车牌号删除
	 */
	@Override
	public String deleteCars(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<String> rr = new ReturnResponse<>();
		try {
			List<Car> list = GsonUtils.jsonToList(json, Car.class);
			System.out.println(123);
			if (baseDao.delete(list) == list.size()) {
				result = rr.extracted(1, "删除数据成功");
			} else {
				result = rr.extracted(0, "删除数据失败");
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "解析json出错");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询单个,但是传递的json数组,返回的也是json数组
	 */
	@Override
	public String queryCar(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<Car> rr = new ReturnResponse<>();
		try {
			List<Car> list = GsonUtils.jsonToList(json, Car.class);
			if (list.size() != 1) {
				result = rr.extracted(0, "数据错误");
			} else {
				List<Car> car = baseDao.query(list.get(0));
				result = (car == null || car.size() != 1) ? rr.extracted(0, "没有查询到数据")
						: rr.extracted(1, car.get(0), null);
			}

		} catch (DataFormatException e) {

			result = rr.extracted(0, "json解析出错");
		}
		return result;
	}

	@Override
	public String updateCar(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<String> rr = new ReturnResponse<>();
		try {
			List<Car> list = GsonUtils.jsonToList(json, Car.class);
			if (list.size() != 1) {
				result = rr.extracted(0, "没有数据||数据错误");
			} else {
				result = baseDao.update(list.get(0)) ? rr.extracted(1, "更新成功") : rr.extracted(0, "更新失败");
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "json解析出错");
		}
		return result;
	}

	@Override
	public String addCar(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<String> rr = new ReturnResponse<>();
		try {
//			List<Car> list = GsonUtils.jsonToList(json, Car.class);
//			if (list.size() != 1) {
//				result = rr.extracted(0, "没有数据||数据错误");
//			} else {
//				result = baseDao. (list.get(0)) ? rr.extracted(1, "添加成功") : rr.extracted(0, "添加失败");
//			}
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			Car car = getMaptoObject(map, Car.class);
			CarUser cu  = new CarUser();
			cu.setId(map.get("car_user_id"));
			car.setUser(cu);
			result = baseDao.insert(car) ? rr.extracted(1, "添加成功") : rr.extracted(0, "添加失败");
		} catch (DataFormatException e) {
			result = rr.extracted(0, "json解析出错");
		} catch (InstantiationException e) {
			result = rr.extracted(0, "FS解析出错");
		} catch (IllegalAccessException e) {
			result = rr.extracted(0, "FS解析出错");
		} catch (IllegalArgumentException e) {
			result = rr.extracted(0, "FS解析出错");
		} catch (InvocationTargetException e) {
			result = rr.extracted(0, "FS解析出错");
		}
		return result;
	}

}

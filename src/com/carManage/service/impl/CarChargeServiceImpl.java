package com.carManage.service.impl;

import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.model.Car;
import com.carManage.model.CarCharge;
import com.carManage.service.CarChargeService;
import com.carManage.service.ResponseType;
import com.carManage.utils.GsonUtils;
/**
 * 车费管理,条件要求
 * @author 47
 * json特殊传递条件命名starttime endtime
 * 添加隐藏域收据单号
 * car_id是外键 id是主键
 * 缴款月改成单个
 */
@Repository("carChargeServiceImpl")
public class CarChargeServiceImpl extends ResponseType implements CarChargeService {
	@Resource(name= "carChargeDAOImpl")
	private BaseDAO<CarCharge,String> baseDao;
	@Override
	public String queryCarCharges(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<List<CarCharge>> rr = new ReturnResponse<>();
		try {
			// 按条件查询的json传输要求为一个对象,即{name:..}
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarCharge cc = getMaptoObject(map, CarCharge.class);
			Car c = new Car();
			c.setId(map.get("car_id"));
			cc.setCar(c);
			List<CarCharge> list = baseDao.query(cc, stringToInteger(map.get("start")), stringToInteger(map.get("count")),
					map.get("starttime"), map.get("endtime"));
			if (list == null || list.size() == 0) {
				result = rr.extracted(0, "没有查到符合条件的数据");
			} else {
				result = rr.extracted(1, list, baseDao.getDataCount(cc));
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化失败");
		} catch (Exception e) {
			result = rr.extracted(0, "反射出问题");
		}
		return result;
	}

	@Override
	public String queryCarCharge(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<CarCharge> rr = new ReturnResponse<>();
		try {
			//此处的car_id是错误值,不需要取,即无效值
			List<CarCharge> list= GsonUtils.jsonToList(json,CarCharge.class);
			if(list.size()!=1){
				result = rr.extracted(0, "前端数据有误");
			} else {
				List<CarCharge> cc = baseDao.query(list.get(0));
				result =(cc==null||cc.size()!=1)?rr.extracted(0, "没有查询到符合条件的数据"): rr.extracted(1, list.get(0), null);
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化失败");
		}
		return result;
	}

	@Override
	public String updateCarCharge(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<CarCharge> rr = new ReturnResponse<>();
		try {
			//此处的car_id是错误值,不需要取,即无效值
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarCharge cc = getMaptoObject(map, CarCharge.class);
			Car c = new Car();
			c.setId(map.get("car_id"));
			cc.setCar(c);
			result=baseDao.update(cc)?rr.extracted(0, "修改失败"):rr.extracted(1,"修改成功");
		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化失败");
		} catch (Exception e) {
			
			result = rr.extracted(0, "反射出问题");
		} 
		return result;
	}

	@Override
	public String insertCarCharge(String json) {
		String result = null;
		// 返回类型需要json包装
		ReturnResponse<CarCharge> rr = new ReturnResponse<>();
		try {
			//此处的car_id是错误值,不需要取,即无效值
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarCharge cc = getMaptoObject(map, CarCharge.class);
			Car c = new Car();
			c.setId(map.get("car_id"));
			cc.setCar(c);
			result=baseDao.insert(cc)?rr.extracted(0, "添加失败"):rr.extracted(1, "添加成功");
		} catch (DataFormatException e) {
			result = rr.extracted(0, "json转化失败");
		} catch (Exception e) {
			result = rr.extracted(0, "反射出问题");
		} 
		return result;
	}

}

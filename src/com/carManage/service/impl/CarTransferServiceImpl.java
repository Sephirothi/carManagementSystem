package com.carManage.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.carManage.dao.BaseDAO;
import com.carManage.dao.BaseDAO.NULL;
import com.carManage.model.Car;
import com.carManage.model.CarTransfer;
import com.carManage.service.CarTransferService;
import com.carManage.service.ResponseType;
import com.carManage.utils.GsonUtils;

@Service("carTransferServiceImpl")
public class CarTransferServiceImpl extends ResponseType implements CarTransferService {
	@Resource(name = "carTransferDAOImpl")
	private BaseDAO<CarTransfer, Date> baseDao;
	@Resource(name = "carDAOImpl")
	private BaseDAO<Car, NULL> cardao;

	@Override
	public String queryCarTransfers(String json) {
		String result = null;
		ReturnResponse<List<CarTransfer>> rr = new ReturnResponse<>();
		try {
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarTransfer ct = getMaptoObject(map, CarTransfer.class);
			Car car = new Car();
			car.setId(map.get("car_id"));
			ct.setCar(car);
			Date o1 = StringToDate(map.get("starttime"));
			Date o2 = StringToDate(map.get("endtime"));
			List<CarTransfer> list = baseDao.query(ct, stringToInteger(map.get("start")),
					stringToInteger(map.get("count")), o1, o2);
			if (list == null||list.size()==0) {
				result = rr.extracted(0, "没有符合条件的数据");
			} else {
				result = rr.extracted(1, list, baseDao.getDataCount(ct));
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--json转化错误");
		} catch (ParseException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射1转化错误");
		} catch (InstantiationException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射2转化错误");
		} catch (IllegalAccessException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射3转化错误");
		} catch (IllegalArgumentException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射4转化错误");
		} catch (InvocationTargetException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射5转化错误");
		} 
		return result;
	}

	@Override
	public String addCarTransfers(String json) {
		String result = null;
		ReturnResponse<CarTransfer> rr = new ReturnResponse<>();
		try {
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarTransfer ct = getMaptoObject(map, CarTransfer.class);
			Car c = new Car();
			c.setId(map.get("id"));
			ct.setCar(c);
			if(baseDao.insert(ct)){
				result = rr.extracted(1, "添加成功");
			}else{
				result = rr.extracted(0, "添加失败");
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--json转化错误");
		} catch (InstantiationException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射错误1");
		} catch (IllegalAccessException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射错误2");
		} catch (IllegalArgumentException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射错误3");
		} catch (InvocationTargetException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--反射错误4");
		}
		return result;
	}

	@Override
	public String querySingle(String json) {
		String result = null;
		ReturnResponse<Car> rr = new ReturnResponse<>();
		try {

			List<Car> car = GsonUtils.jsonToList(json, Car.class);
			if (car.size() != 1) {
				result = rr.extracted(0, "传输数据有误");
			} else {
				List<Car> list = cardao.query(car.get(0));
				System.out.println(list.size());
				System.out.println(list.get(0).getBrand());
				result = (list==null||list.size()!=1)?rr.extracted(0, "没有符合条件的数据"):rr.extracted(1, list.get(0),null);
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--json转化错误");
		}
		return result;
	}

}

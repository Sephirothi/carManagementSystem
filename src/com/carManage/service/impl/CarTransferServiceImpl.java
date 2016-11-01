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
	@Resource(name = "CarDAOImpl")
	private BaseDAO<Car, NULL> cardao;

	@Override
	public String queryCarTransfers(String json) {
		String result = null;
		ReturnResponse<List<CarTransfer>> rr = new ReturnResponse<>();
		try {
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarTransfer ct = getMaptoObject(map, CarTransfer.class);
			// System.out.println(ct);
			Car car = new Car();
			car.setId(map.get("id"));
			// System.out.println(car);
			// Integer start = Integer.parseInt();//起始页数
			// Integer count = Integer.parseInt(map.get("count"));//每页长度
			Date o1 = StringToDate(map.get("starttime"));
			Date o2 = StringToDate(map.get("endtime"));
			List<CarTransfer> list = baseDao.query(ct, stringToInteger(map.get("start")),
					stringToInteger(map.get("count")), o1, o2);
			// List<CarTransfer> list= null;
			if (list == null) {
				result = rr.extracted(0, "没有符合条件的数据");
			} else {
				result = rr.extracted(1, list, baseDao.getDataCount(ct));
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
		} catch (ParseException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--时间转化错误5");
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
			result = rr.extracted(1, ct, null);
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

			Car car = GsonUtils.jsonToObject(json, Car.class);
			List<Car> list = cardao.query(car);
			if (list == null || list.size() != 1) {
				result = rr.extracted(0, "没有符合类型的数据");
			} else {
				result = rr.extracted(1, list.get(0), cardao.getDataCount(car));
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--json转化错误");
		}
		return result;
	}

}

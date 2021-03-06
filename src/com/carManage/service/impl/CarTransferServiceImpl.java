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
import com.carManage.model.CarUser;
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
			System.out.println(json);
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarTransfer ct = getMaptoObject(map, CarTransfer.class);
			//ct.setCarId(map.get("carId"));
			Date o1 = StringToDate(map.get("starttime"));
			Date o2 = StringToDate(map.get("endtime"));
			List<CarTransfer> list = baseDao.query(ct, stringToInteger(map.get("start")),
					stringToInteger(map.get("count")), o1, o2);
			if (list == null || list.size() == 0) {
				result = rr.extracted(0, "没有符合条件的数据");
			} else {
				result = rr.extracted(1, list, baseDao.getDataCountWithLimit(ct, o1, o2));
				// result = rr.extracted(1, list, baseDao.getDataCount(ct));
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

	/**
	 * 单个对象json
	 */
	@Override
	public String addCarTransfers(String json) {
		String result = null;
		ReturnResponse<CarTransfer> rr = new ReturnResponse<>();
		try {
			Map<String, String> map = GsonUtils.jsonToMaps(json);
			CarTransfer ct = getMaptoObject(map, CarTransfer.class);
			ct.setCarId(map.get("carId"));
			ct.setTransfer_time(StringToDate(map.get("transfer_time")));

			Car car = getMaptoObject(map, Car.class);
			car.setId(map.get("carId"));
			CarUser cu = new CarUser();
			cu.setId(map.get("new_user_id"));
			car.setUser(cu);

			String mess = "";

			if (baseDao.insert(ct)) {
				mess = mess + "CarTransfer表更新成功,";
				if (cardao.update(car)) {
					mess = mess + "Car表更新成功!";
					result = rr.extracted(1, mess);
				} else {
					mess = mess + "Car表更新失败!";
					result = rr.extracted(0, mess);
				}
			} else {
				mess = mess + "CarTransfer表更新失败,";
				if (cardao.update(car)) {
					mess = mess + "Car表更新成功!";
					result = rr.extracted(1, mess);
				} else {
					mess = mess + "Car表更新失败!";
					result = rr.extracted(0, mess);
				}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String querySingle(String json) {
		String result = null;
		ReturnResponse<List<Car>> rr = new ReturnResponse<>();
		try {

			List<Car> car = GsonUtils.jsonToList(json, Car.class);
			if (car.size() != 1) {
				result = rr.extracted(0, "传输数据有误");
			} else {
				List<Car> list = cardao.query(car.get(0));
				// System.out.println(list.size());
				// System.out.println(list.get(0).getBrand());
				result = (list == null || list.size() != 1) ? rr.extracted(0, "没有符合条件的数据")
						: rr.extracted(1, list, null);
			}
		} catch (DataFormatException e) {
			result = rr.extracted(0, "CarTransferServiceImpl--json转化错误");
		}
		return result;
	}
}

package com.carManage.dao.impl;

import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.model.CarCharge;

/**
 * 管理费页面
 * 限制条件：月份，类型String
 * @author admin
 *
 */
@Repository("carChargeDAOImpl")
public class CarChargeDAOImpl extends BaseDAO<CarCharge, String>{
	
}

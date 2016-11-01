package com.carManage.dao.impl;

import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.dao.BaseDAO.NULL;
import com.carManage.model.Car;

/**
 * 车辆查询页面
 * 限制条件:无
 * @author admin
 *
 */
@Repository("carDAOImpl")
public class CarDAOImpl extends BaseDAO<Car, NULL>{

}

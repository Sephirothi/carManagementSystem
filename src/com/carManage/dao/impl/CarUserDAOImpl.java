package com.carManage.dao.impl;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.model.CarUser;
/**
 * 车主
 * 限制条件：生日，date类型
 * @author admin
 *
 */
@Repository("carUserDAOImpl")
public class CarUserDAOImpl extends BaseDAO<CarUser, Date>{

}

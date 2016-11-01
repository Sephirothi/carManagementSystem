package com.carManage.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.carManage.dao.BaseDAO;
import com.carManage.model.CarTransfer;

/**
 * 这里还是传入了业务实体
 * @author admin
 *
 */
@Repository("carTransferDAOImpl")
public class CarTransferDAOImpl extends BaseDAO<CarTransfer, Date> {

	@Override
	public boolean update(CarTransfer car) {
		return false;
	}

	
	
	@Override
	public int delete(List<CarTransfer> t) {
		// TODO Auto-generated method stub
		return super.delete(t);
	}

	@Override
	public boolean insert(CarTransfer c) {
		return super.insert(c);
	}

	@Override
	public List<CarTransfer> query(CarTransfer t) {
		// TODO Auto-generated method stub
		return super.query(t);
	}

	@Override
	public List<CarTransfer> query(CarTransfer t, int start, int count,
			Date o1, Date o2) {
		// TODO Auto-generated method stub
		return super.query(t, start, count, o1, o2);
	}

	@Override
	public long getDataCount(CarTransfer t) {
		// TODO Auto-generated method stub
		return super.getDataCount(t);
	}
	
}

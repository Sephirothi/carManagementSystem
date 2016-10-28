package com.carManage.model.BO;

import java.util.Date;

/**
 * 业务数据对象类 用于车辆变更列表顶部的查询
 * 
 * @author admin
 *
 */
public class CarTransferBO {
	// 车牌号
	private String carId;
	// 原车主
	private String oldCarUserId;
	// 新车主的身份证号
	private String newCarUserId;
	// 转让日期开始
	private Date transferDateStart;
	// 转让日期结尾
	private Date transferDateEnd;

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getNewUserId() {
		return newCarUserId;
	}

	public void setNewUserId(String newCarUserId) {
		this.newCarUserId = newCarUserId;
	}

	public Date getTransferDateStart() {
		return transferDateStart;
	}

	public String getOldCarUserId() {
		return oldCarUserId;
	}

	public void setOldCarUserId(String oldCarUserId) {
		this.oldCarUserId = oldCarUserId;
	}

	public String getNewCarUserId() {
		return newCarUserId;
	}

	public void setNewCarUserId(String newCarUserId) {
		this.newCarUserId = newCarUserId;
	}

	public void setTransferDateStart(Date transferDateStart) {
		this.transferDateStart = transferDateStart;
	}

	public Date getTransferDateEnd() {
		return transferDateEnd;
	}

	public void setTransferDateEnd(Date transferDateEnd) {
		this.transferDateEnd = transferDateEnd;
	}

}

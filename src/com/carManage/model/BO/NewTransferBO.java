package com.carManage.model.BO;

import java.util.Date;

/**
 * 业务数据对象类
 * 用于“新增车辆变更”页面的数据类对象
 * @author admin
 *
 */
public class NewTransferBO {
	// 车牌号
	private String carId;
	// 老车主
	private String oldCarUser;
	// 现车主
	private String newCarUser;
	// 转让费
	private int transferFee;
	// 转让时间
	private Date transferDate;
	// 行车证号
	private String driverNumber;
	// 燃油类型
	private String fuelType;
	// 座位数
	private int seatCount;
	// 车身状况
	private String carState;
	// 车辆颜色
	private String carColor;
	// 登记时间
	private Date startTime;
	// 车辆照片s	
	private String picUrls;
	// 备注
	private String comment;
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getOldCarUser() {
		return oldCarUser;
	}
	public void setOldCarUser(String oldCarUser) {
		this.oldCarUser = oldCarUser;
	}
	public String getNewCarUser() {
		return newCarUser;
	}
	public void setNewCarUser(String newCarUser) {
		this.newCarUser = newCarUser;
	}
	public int getTransferFee() {
		return transferFee;
	}
	public void setTransferFee(int transferFee) {
		this.transferFee = transferFee;
	}
	public Date getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	public String getDriverNumber() {
		return driverNumber;
	}
	public void setDriverNumber(String driverNumber) {
		this.driverNumber = driverNumber;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public int getSeatCount() {
		return seatCount;
	}
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}
	public String getCarState() {
		return carState;
	}
	public void setCarState(String carState) {
		this.carState = carState;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getPicUrls() {
		return picUrls;
	}
	public void setPicUrls(String picUrls) {
		this.picUrls = picUrls;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}

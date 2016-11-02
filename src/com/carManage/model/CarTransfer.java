package com.carManage.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class CarTransfer {
	@Id
	@GeneratedValue(generator = "CarTransfer")
	@GenericGenerator(name = "CarTransfer", strategy = "increment")
	private Integer id;//表示唯一实体,无实际意义
//	@ManyToOne(targetEntity=Car.class)
//	private Car car;//外键,连接car表
//	@JoinColumn(name="car_id")
//	private Car car = new Car();//外键,连接car表
	private String carId;// 车辆的外键
	private String comment;//备注
	private String transfer_fee;//过户费用
	
	private String old_user_id;//旧车主的身份证(也就是转换时的现车主，转换后的旧车主)
	
	private String new_user_id;//新车主身份证明
	private Date transfer_time;
	public CarTransfer(){
		
	}
	public CarTransfer(Integer id, String carId, String comment, String transfer_fee, String new_user_id,Date transfer_time) {
		super();
		this.id = id;
		this.carId = carId;
		this.comment = comment;
		this.transfer_fee = transfer_fee;
		this.new_user_id = new_user_id;
		this.transfer_time = transfer_time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOld_user_id() {
		return old_user_id;
	}
	public void setOld_user_id(String old_user_id) {
		this.old_user_id = old_user_id;
	}
	
	
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTransfer_fee() {
		return transfer_fee;
	}
	public void setTransfer_fee(String transfer_fee) {
		this.transfer_fee = transfer_fee;
	}
	public String getNew_user_id() {
		return new_user_id;
	}
	public void setNew_user_id(String new_user_id) {
		this.new_user_id = new_user_id;
	}
	@Override
	public String toString() {
		return "CarTransfer [id=" + id + ", car=" + carId + ", comment=" + comment + ", transfer_fee=" + transfer_fee
				+ ", new_user_id=" + new_user_id + "]";
	}
	public Date getTransfer_time() {
		return transfer_time;
	}
	public void setTransfer_time(Date transfer_time) {
		this.transfer_time = transfer_time;
	}
	
}

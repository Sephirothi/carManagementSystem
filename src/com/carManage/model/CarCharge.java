package com.carManage.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CarCharge {
	@Id
	private String recript_id;//PK,收据号
	@ManyToOne(fetch=FetchType.LAZY,
			targetEntity=Car.class,
			cascade=CascadeType.ALL
		)
	@JoinColumn(name="car_id")
	private Car car;//车辆的外键
	private Date pay_time;//缴费时间
	private String pay_type;//缴费类型
	private String hand_fee_person;//交款人
	private String pay_fee;//缴费金额
	private String pay_year;//交款年
	private Integer pay_month;//交款月
	private String gather_person;//收款人
	private String comment;//备注
	public CarCharge(){}
	public CarCharge(String recript_id, Car car, Date pay_time, String pay_type, String hand_fee_person, String pay_fee,
			String pay_year, Integer pay_month, String gather_person, String comment) {
		super();
		this.recript_id = recript_id;
		this.car = car;
		this.pay_time = pay_time;
		this.pay_type = pay_type;
		this.hand_fee_person = hand_fee_person;
		this.pay_fee = pay_fee;
		this.pay_year = pay_year;
		this.pay_month = pay_month;
		this.gather_person = gather_person;
		this.comment = comment;
	}
	public String getRecript_id() {
		return recript_id;
	}
	public void setRecript_id(String recript_id) {
		this.recript_id = recript_id;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getHand_fee_person() {
		return hand_fee_person;
	}
	public void setHand_fee_person(String hand_fee_person) {
		this.hand_fee_person = hand_fee_person;
	}
	public String getPay_fee() {
		return pay_fee;
	}
	public void setPay_fee(String pay_fee) {
		this.pay_fee = pay_fee;
	}
	public String getPay_year() {
		return pay_year;
	}
	public void setPay_year(String pay_year) {
		this.pay_year = pay_year;
	}
	public Integer getPay_month() {
		return pay_month;
	}
	public void setPay_month(Integer pay_month) {
		this.pay_month = pay_month;
	}
	public String getGather_person() {
		return gather_person;
	}
	public void setGather_person(String gather_person) {
		this.gather_person = gather_person;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "CarCharge [recript_id=" + recript_id + ", car=" + car + ", pay_time=" + pay_time + ", pay_type="
				+ pay_type + ", hand_fee_person=" + hand_fee_person + ", pay_fee=" + pay_fee + ", pay_year=" + pay_year
				+ ", pay_month=" + pay_month + ", gather_person=" + gather_person + ", comment=" + comment + "]";
	}
	public void update(CarCharge cc) {
		this.pay_type = cc.pay_type == null ? this.pay_type : cc.pay_type;
		this.gather_person = cc.gather_person == null ? this.gather_person : cc.gather_person;
		this.pay_year = cc.pay_year == null ? this.pay_year : cc.pay_year;
		this.pay_month = cc.pay_month == null ? this.pay_month : cc.pay_month;
		this.comment = cc.comment == null ? this.comment : cc.comment;
	}
}

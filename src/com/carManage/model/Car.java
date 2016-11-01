package com.carManage.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Car {
//	@GeneratedValue(generator = "generator")
//	@GenericGenerator(name = "generator", strategy = "assigned")
	@Id
	private String id;// 车牌号,主键,必填
	
	@ManyToOne(fetch=FetchType.LAZY,
				targetEntity=CarUser.class,
				cascade=CascadeType.ALL
			)
	@JoinColumn(name="car_user_id")
	private CarUser user = new CarUser();// 车主外键,必填
	@Column(nullable = false)
	private String car_type;// 车辆类型,不能为空
	@Column(nullable = false)
	private String brand;// 车辆品牌,不能为空
	private String engine_number;// 引擎型号
	private String drive_number;// 行车证号
	private String fuel_type;// 燃油类型
	private String seat_count;// 座位数
	private String car_state;// 车身状况
	private String car_color;// 车身颜色
	@Column(nullable = false)
	private Date create_time;// 登记时间,不能为空
	private String comment;// 备注
	@Column(nullable = false)
	private String pic_urls;// 车辆照片url必填项,不能为空

	public Car() {
	}

	public Car(String id, CarUser user, String car_type, String brand, String engine_number, String drive_number,
			String fuel_type, String seat_count, String car_state, String car_color, Date create_time, String comment,
			String pic_urls) {
		super();
		this.id = id;
		this.user = user;
		this.car_type = car_type;
		this.brand = brand;
		this.engine_number = engine_number;
		this.drive_number = drive_number;
		this.fuel_type = fuel_type;
		this.seat_count = seat_count;
		this.car_state = car_state;
		this.car_color = car_color;
		this.create_time = create_time;
		this.comment = comment;
		this.pic_urls = pic_urls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CarUser getUser() {
		return user;
	}

	public void setUser(CarUser user) {
		this.user = user;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getEngine_number() {
		return engine_number;
	}

	public void setEngine_number(String engine_number) {
		this.engine_number = engine_number;
	}

	public String getDrive_number() {
		return drive_number;
	}

	public void setDrive_number(String drive_number) {
		this.drive_number = drive_number;
	}

	public String getFuel_type() {
		return fuel_type;
	}

	public void setFuel_type(String fuel_type) {
		this.fuel_type = fuel_type;
	}

	public String getSeat_count() {
		return seat_count;
	}

	public void setSeat_count(String seat_count) {
		this.seat_count = seat_count;
	}

	public String getCar_state() {
		return car_state;
	}

	public void setCar_state(String car_state) {
		this.car_state = car_state;
	}

	public String getCar_color() {
		return car_color;
	}

	public void setCar_color(String car_color) {
		this.car_color = car_color;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPic_urls() {
		return pic_urls;
	}

	public void setPic_urls(String pic_urls) {
		this.pic_urls = pic_urls;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", user=" + user + ", car_type=" + car_type + ", brand=" + brand + ", engine_number="
				+ engine_number + ", drive_number=" + drive_number + ", fuel_type=" + fuel_type + ", seat_count="
				+ seat_count + ", car_state=" + car_state + ", car_color=" + car_color + ", create_time=" + create_time
				+ ", comment=" + comment + ", pic_urls=" + pic_urls + "]";
	}
}

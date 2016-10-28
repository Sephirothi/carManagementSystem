package com.carManage.model.BO;

import java.util.Date;

/**
 * 业务数据对象类
 * 用于车主列表顶部的查询
 * @author admin
 *
 */
public class CarUserBO {
	// 姓名
	private String name;
	// 性别
	private String sex;
	// 电话
	private String phone;
	// 出生日期开始
	private Date birthDayStart;
	// 出生日期结尾
	private Date birthDayEnd;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthDayStart() {
		return birthDayStart;
	}

	public void setBirthDayStart(Date birthDayStart) {
		this.birthDayStart = birthDayStart;
	}

	public Date getBirthDayEnd() {
		return birthDayEnd;
	}

	public void setBirthDayEnd(Date birthDayEnd) {
		this.birthDayEnd = birthDayEnd;
	}

}

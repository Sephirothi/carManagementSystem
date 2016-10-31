package com.carManage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author 47 用户,即管理员表映射的实体类
 */
@Entity
public class User {
	@Id
	@GeneratedValue(generator = "generator")    
	@GenericGenerator(name = "generator", strategy = "increment") 
	private Integer id;// 判定实体的唯一性,没有实际含义
	@Column(unique = true, nullable = false, length = 30)
	private String username;// 用户名,不能为空,且唯一
	@Column(nullable = false, length = 30)
	private String password;// 用户登录密码,不能为空
	@Column(nullable = false, length = 15)
	private String name;// 用户真实姓名
	@Column(nullable = false,length = 5)
	private String sex;// 用户性别
	@Column(nullable = false,columnDefinition="varchar(6) default '可用'")
	private String state;// 用户状态,可用或者禁用
	@Column(nullable = false,columnDefinition="varchar(10) default '系统管理员'")
	private String authority;// 用户权限
	private Date date;//创建时间
	public User() {

	}

	public User(Integer id, String username, String password, String name, String sex, String state, String authority, Date date) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.state = state;
		this.authority = authority;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", sex="
//				+ sex + ", state=" + state + ", authority=" + authority + "]";
		return "User [username=" + username + ", name=" + name + "]";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void updateUser(User user) {
		this.password = user.password == null ? this.password : user.password;
		this.name = user.name == null ? this.name : user.name;
		this.sex = user.sex == null ? this.sex : user.sex;
		this.state = user.state == null ? this.state : user.state;
		this.authority = user.authority == null ? this.authority : user.authority;
	}
}

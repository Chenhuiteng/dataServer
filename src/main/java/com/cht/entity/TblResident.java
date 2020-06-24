package com.cht.entity;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_resident")
public class TblResident implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*
	 * `sid` int(11) NOT NULL AUTO_INCREMENT, `name` varchar(50) NOT NULL, `age`
	 * smallint(6) DEFAULT NULL, `area` varchar(255) DEFAULT NULL, `birth_time`
	 * datetime DEFAULT NULL,
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sid")
	private Long sid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "birth_time")
	private Time birthTime;
	
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public Time getBirthTime() {
		return birthTime;
	}
	public void setBirthTime(Time birthTime) {
		this.birthTime = birthTime;
	}


	public TblResident(String name, int age, String area, Time birthTime, String userName, String passWord) {
		super();
		this.name = name;
		this.age = age;
		this.area = area;
		this.birthTime = birthTime;
		this.userName = userName;
		this.passWord = passWord;
	}

	public TblResident() {
		super();
	}




	@Column(name = "user_name")
	private String userName;
	@Column(name = "pass_word")
	private String passWord;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Override
	public String toString() {
		return "TblResident{" +
				"sid=" + sid +
				", name='" + name + '\'' +
				", age=" + age +
				", area='" + area + '\'' +
				", birthTime=" + birthTime +
				", userName='" + userName + '\'' +
				", passWord='" + passWord + '\'' +
				'}';
	}
}

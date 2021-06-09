package com.ukg.testbed.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "bsnlemp")
public class EmpEntity {
	private static final long serialVersionUID = 4865903039190150223L;
	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Integer phoneNumber;
	@Column(nullable = false)
	private String empCode;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

}

package com.ukg.testbed.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

	private String empCode;
	private String name;

	private Integer phoneNumber;	
	private List<String> projects = new ArrayList<String>();
	
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
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

	@Override
	public String toString() {
		return "EmpSaveResponseModel [empCode=" + empCode + ", name=" + name + ", phoneNumber=" + phoneNumber + "]";
	}

	public void addProjects(String projectName) {
		projects.add(projectName);
	}

	public List<String> getProjects() {
		return projects;
	}

	
}

package com.ukg.testbed.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.istack.Nullable;
import com.ukg.testbed.dto.EmpDTO;
import com.ukg.testbed.model.Applicant;
import com.ukg.testbed.model.EmployeeModel;
import com.ukg.testbed.service.api.EmployeeService;
import com.ukg.testbed.utils.ObjectConverter;

@RestController
@RequestMapping("emp")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/create")
	private void registerNewEmployeeAutoInThread() {
		EmployeeModel responsEmpDetails = new EmployeeModel();
		EmpDTO empDto = new EmpDTO();
		empDto.setName("Mr. X");
		empDto.setPhoneNumber(90000);
		empDto.setEmpCode(UUID.randomUUID().toString());
		EmpDTO createdEmp =	employeeService.createEmployee(empDto);	
	}

	
	
	
}

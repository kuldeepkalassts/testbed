package com.ukg.testbed.service.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ukg.testbed.dto.EmpDTO;
import com.ukg.testbed.entity.EmpEntity;
import com.ukg.testbed.repository.api.EmpRepository;
import com.ukg.testbed.service.api.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final EmpRepository empRepository;

	private final RabbitMQSender rabbitMQSender;
	
	@Autowired
	public EmployeeServiceImpl(EmpRepository empRepository,RabbitMQSender rabbitMQSender) {
		this.empRepository = empRepository;
		this.rabbitMQSender = rabbitMQSender;
	}

	@Override
	@Transactional
	public EmpDTO createEmployee(EmpDTO empDTO) {		
		EmpDTO returnValue = new EmpDTO();
		String userId = UUID.randomUUID().toString();
		empDTO.setEmpCode(userId);

		EmpEntity empEntity = new EmpEntity();
		BeanUtils.copyProperties(empDTO, empEntity);

		// Record data into a database
		System.out.println("Is TX Active "+org.springframework.transaction.support.TransactionSynchronizationManager.getCurrentTransactionName());
		empEntity = empRepository.save(empEntity);
		BeanUtils.copyProperties(empEntity, returnValue);
		rabbitMQSender.send(empDTO);
		return returnValue;
	}
	
}

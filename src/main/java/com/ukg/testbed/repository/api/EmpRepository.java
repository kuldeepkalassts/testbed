package com.ukg.testbed.repository.api;

import org.springframework.data.repository.CrudRepository;

import com.ukg.testbed.entity.EmpEntity;

public interface EmpRepository extends CrudRepository<EmpEntity, Long> {
// Only curd for now
}

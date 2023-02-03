package com.dolphinskart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dolphinskart.entity.Employee;

public interface EmployeeRepo extends MongoRepository<Employee, Integer> {

}

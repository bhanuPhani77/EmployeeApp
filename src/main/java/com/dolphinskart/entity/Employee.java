package com.dolphinskart.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Employee")
public class Employee {
	@Id
 private int empId;
	@NonNull
 private String empName;
	@NonNull
 private int empAge;
	@NonNull
 private String empDept;
	@NonNull
 private Double empSalary;
}

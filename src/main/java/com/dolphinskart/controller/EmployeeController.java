package com.dolphinskart.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dolphinskart.entity.Employee;
import com.dolphinskart.excelGenerator.EmployeeExcelGenerator;
import com.dolphinskart.repository.EmployeeRepo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	private EmployeeRepo repo;

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployee() {
		List<Employee> findAll = repo.findAll();
		return ResponseEntity.ok(findAll);
	}

	@GetMapping("/employees/{id}")
	public Optional<Employee> getOneEmployee(@PathVariable int id) {
		Optional<Employee> findById = repo.findById(id);
		int empId = findById.get().getEmpId();
		if ((id != empId) || (id < 0)) {
			throw new EmployeeNotFoundException("Employee with id: " + id + " not found.");
		}
		return repo.findById(id);
	}

	@GetMapping("/emp")
	public ResponseEntity<Page<Employee>> retrieveAll(/*@RequestParam(required = false) String empName,
			@RequestParam(required = false) Integer empAge, @RequestParam(required = false) Integer empDept,
			@RequestParam(required = false) Integer empSalary, */@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 5);
		return ResponseEntity.ok(repo.findAll(pageable));
	}

	@GetMapping("export-to-excel")
	public void exportIntoFile(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=employee" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Employee> listOfStudents = repo.findAll();
		EmployeeExcelGenerator generator = new EmployeeExcelGenerator(listOfStudents);
		generator.generate(response);
	}

	@PostMapping("/employees")
	public String saveEmployee(@Validated @RequestBody Employee employee) {
		repo.save(employee);
		return "employee added successfully";
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmp(@PathVariable int id) {
		Optional<Employee> findById = repo.findById(id);
		int empId = findById.get().getEmpId();
		if (id == empId) {
			repo.deleteById(empId);
		}
		return new ResponseEntity<String>("Employee deleted with ID-> " + id, HttpStatus.OK);
	}
}
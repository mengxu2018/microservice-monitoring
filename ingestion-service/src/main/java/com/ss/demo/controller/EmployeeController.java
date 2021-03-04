package com.ss.demo.controller;

import com.ss.demo.bean.Employee;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuhang
 */
@RestController
@RequestMapping(value = "employee")
public class EmployeeController {

	@Autowired
	private MeterRegistry meterRegistry;

	private List<Employee> employeeList = new ArrayList();

	private Integer id = 0;

	private Counter addEmployeeCounter;
	private Counter deleteEmployeeCounter;
	private Counter updateEmployeeCounter;
	private Counter listEmployeeCounter;

	@PostConstruct
	public void initialize() {
		System.out.println("test");
		System.out.println(meterRegistry);
		// 2- create a counter using the fluent API
		addEmployeeCounter = Counter.builder("employee.add")
//				.tag("type", "ale")
				.description("The number of employees added")
				.register(meterRegistry);

		deleteEmployeeCounter = Counter.builder("employee.delete")
				.description("The number of employees deleted")
				.register(meterRegistry);

		updateEmployeeCounter = Counter.builder("employee.update")
				.description("The number of employees updated")
				.register(meterRegistry);

		listEmployeeCounter = Counter.builder("employee.list")
				.description("The number of employees listed")
				.register(meterRegistry);
	}


	@PostMapping("/add")
	public Employee add(Employee employee) {
		employee.setId(++id);
		employeeList.add(employee);
		addEmployeeCounter.increment();
		return employee;
	}

	@PostMapping("/update")
	public Employee update(Employee employee) {
//		employee.setId(++id);
//		employeeList.add(employee);
		updateEmployeeCounter.increment();
		return employee;
	}

	@PostMapping("/delete")
	public Employee delete(Employee employee) {
		employeeList.removeIf(p -> p.getId().equals(employee.getId()));
		deleteEmployeeCounter.increment();
		return employee;
	}

	@GetMapping("/list")
	public List<Employee> list(Employee employee) {
		listEmployeeCounter.increment();
		return employeeList;
	}


}
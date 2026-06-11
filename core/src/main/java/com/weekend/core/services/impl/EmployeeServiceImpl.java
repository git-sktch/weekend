package com.weekend.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import com.weekend.core.models.Employee;
import com.weekend.core.services.EmployeeService;

@Component(service = EmployeeService.class)
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public List<Employee> getEmployees() {

        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "John", "Developer", "john@test.com"));
        employees.add(new Employee(2, "David", "Tester", "david@test.com"));
        employees.add(new Employee(3, "Smith", "Manager", "smith@test.com"));

        return employees;
    }
}
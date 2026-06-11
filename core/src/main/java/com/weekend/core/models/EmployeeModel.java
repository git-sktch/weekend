package com.weekend.core.models;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.weekend.core.services.EmployeeService;

@Model(adaptables = SlingHttpServletRequest.class)
public class EmployeeModel {

    @OSGiService
    private EmployeeService employeeService;

    private List<Employee> employees;

    @PostConstruct
    protected void init() {

        employees = employeeService.getEmployees();

    }

    public List<Employee> getEmployees() {
        return employees;
    }

}
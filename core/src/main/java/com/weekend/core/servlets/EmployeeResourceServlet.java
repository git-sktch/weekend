package com.weekend.core.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.weekend.core.models.Employee;
import com.weekend.core.services.EmployeeService;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/employees",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET
        }
)
public class EmployeeResourceServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG =
            LoggerFactory.getLogger(EmployeeResourceServlet.class);

    @Reference
    private EmployeeService employeeService;

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws ServletException, IOException {

        LOG.info("Employee Servlet Started");

        List<Employee> employees = employeeService.getEmployees();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();

        response.getWriter().write(gson.toJson(employees));

        LOG.info("Employee Servlet Completed");
    }
}
package com.demo.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.osgi.service.component.annotations.Component;

import org.apache.sling.servlets.annotations.SlingServletResourceTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class)

@SlingServletResourceTypes(

        resourceTypes = "weekend/components/page",

        selectors = "data",

        extensions = "json",

        methods = "GET"
)

public class DemoServlet extends SlingAllMethodsServlet {

    private static final Logger log =
            LoggerFactory.getLogger(DemoServlet.class);

    @Override
    protected void doGet(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        log.info("DemoServlet execution started");

        response.setContentType("application/json");

        response.getWriter().write(
                "{\"message\":\"Hello Sling Servlet\"}"
        );

        log.debug("JSON response sent successfully");
    }
}
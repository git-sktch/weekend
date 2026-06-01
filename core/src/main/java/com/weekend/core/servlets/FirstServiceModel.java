package com.weekend.core.servlets;

import com.weekend.core.services.FirstService;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import org.apache.sling.servlets.annotations.SlingServletResourceTypes;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = Servlet.class)

@SlingServletResourceTypes(
        resourceTypes = "weekend/components/page",
        methods = HttpConstants.METHOD_GET,
        extensions = "txt"
)

public class FirstServiceModel extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private FirstService firstService;

    @Override
    protected void doGet(
            final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/plain");

        resp.getWriter().write(
                  firstService.getMessage()
        );
    }
}
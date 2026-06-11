package com.weekend.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.osgi.service.component.annotations.Component;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.resourceTypes=weekend/components/new-api",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.extensions=json"
        })
public class NewApiServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json =
                "{"
                        + "\"products\":["
                        + "{"
                        + "\"name\":\"Laptop\","
                        + "\"price\":\"50000\""
                        + "},"
                        + "{"
                        + "\"name\":\"Mobile\","
                        + "\"price\":\"25000\""
                        + "},"
                        + "{"
                        + "\"name\":\"Headphones\","
                        + "\"price\":\"3000\""
                        + "},"
                        + "{"
                        + "\"name\":\"Smart Watch\","
                        + "\"price\":\"12000\""
                        + "}"
                        + "]"
                        + "}";

        response.getWriter().write(json);

    }

}
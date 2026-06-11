package com.weekend.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class)
@org.apache.sling.servlets.annotations.SlingServletResourceTypes(
        resourceTypes = "weekend/components/products",
        selectors = "products",
        extensions = "json",
        methods = HttpConstants.METHOD_GET
)
public class ProductServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ValueMap props = request.getResource().getValueMap();

        String[] cars = props.get("car", String[].class);
        String[] prices = props.get("price", String[].class);
        String[] colors = props.get("color", String[].class);

        StringBuilder json = new StringBuilder("[");

        if (cars != null) {
            for (int i = 0; i < cars.length; i++) {

                json.append("{")
                        .append("\"car\":\"").append(cars[i]).append("\",");

                json.append("\"price\":\"")
                        .append(prices != null && i < prices.length ? prices[i] : "")
                        .append("\",");

                json.append("\"color\":\"")
                        .append(colors != null && i < colors.length ? colors[i] : "")
                        .append("\"")
                        .append("}");

                if (i < cars.length - 1) {
                    json.append(",");
                }
            }
        }

        json.append("]");

        response.getWriter().write(json.toString());
    }
}
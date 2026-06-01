package com.weekend.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.apache.sling.servlets.annotations.SlingServletResourceTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weekend.core.services.ArticleService;

@Component(service = Servlet.class)

@SlingServletResourceTypes(
        resourceTypes = "weekend/components/articlecomponent",
        selectors = "data",
        extensions = "json"
)

public class ArticleServlet extends SlingSafeMethodsServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(ArticleServlet.class);

    @Reference
    private ArticleService articleService;

    @Override
    protected void doGet(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws ServletException, IOException {

        LOG.info("========== Servlet Request Started ==========");

        response.setContentType("application/json");

        try {

            LOG.info("Calling ArticleService");

            String msg = articleService.getMessage();

            LOG.debug("Message from service : {}", msg);

            String jsonResponse =
                    "{\"message\":\"" + msg + "\"}";

            LOG.info("Sending JSON Response");

            response.getWriter().write(jsonResponse);

        } catch (Exception e) {

            LOG.error("Error occurred in servlet", e);

        }

        LOG.info("========== Servlet Request Completed ==========");
    }
}

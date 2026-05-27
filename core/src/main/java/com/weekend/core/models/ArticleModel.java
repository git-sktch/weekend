package com.weekend.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weekend.core.services.ArticleService;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)

public class ArticleModel {

    // Logger Object
    private static final Logger LOG =
            LoggerFactory.getLogger(ArticleModel.class);

    // Dialog Property
    @ValueMapValue
    private String title;

    // OSGi Service Injection
    @OSGiService
    private ArticleService articleService;

    private String message;

    @PostConstruct
    protected void init() {

        LOG.info("========== ArticleModel INIT Started ==========");

        try {

            LOG.debug("Author Entered Title : {}", title);

            if (articleService != null) {

                LOG.info("ArticleService injected successfully");

                message = articleService.getMessage();

                LOG.info("Message received from service : {}", message);

            } else {

                LOG.error("ArticleService injection failed");

                message = "Service Not Available";
            }

        } catch (Exception e) {

            LOG.error("Exception occurred inside ArticleModel", e);

        }

        LOG.info("========== ArticleModel INIT Completed ==========");
    }

    public String getTitle() {

        LOG.debug("getTitle() called");

        return title;
    }

    public String getMessage() {

        LOG.debug("getMessage() called");

        return message;
    }
}

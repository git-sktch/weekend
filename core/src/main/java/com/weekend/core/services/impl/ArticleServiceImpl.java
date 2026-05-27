package com.weekend.core.services.impl;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weekend.core.services.ArticleService;

@Component(service = ArticleService.class)

public class ArticleServiceImpl implements ArticleService {

    private static final Logger LOG =
            LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Override
    public String getMessage() {

        LOG.info("getMessage() method started");

        String msg = "Welcome from OSGi Service";

        LOG.debug("Returning Message : {}", msg);

        LOG.info("getMessage() method completed");

        return msg;
    }
}

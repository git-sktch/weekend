package com.weekend.core.services.impl;

import org.osgi.service.component.annotations.Component;

import com.weekend.core.services.FirstService;

@Component(service = FirstService.class)
public class FirstServiceImpl implements FirstService {

    @Override
    public String getMessage() {
        return "This is my first service model";
    }
}

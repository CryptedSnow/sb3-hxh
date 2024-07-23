package com.springboot3.sb3hxh.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Collections;

@Configuration
public class IconConfiguration {

    @Bean
    public SimpleUrlHandlerMapping customIcon () {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(Integer.MIN_VALUE);
        simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/static/favicon.ico", favIconRequest()));
        return simpleUrlHandlerMapping;
    }

    protected ResourceHttpRequestHandler favIconRequest() {
        ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
        resourceHttpRequestHandler.setLocations(Collections.singletonList(new ClassPathResource("/")));
        return resourceHttpRequestHandler;
    }

}
package com.dnb.jdbc.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.dnb.jdbc.demo")
@PropertySource("application.properties")

public class Config {
    @Autowired
    public Environment environment;
}

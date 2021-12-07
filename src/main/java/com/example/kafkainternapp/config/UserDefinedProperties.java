package com.example.kafkainternapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:${app_settings}", ignoreResourceNotFound = true)
public class UserDefinedProperties {}

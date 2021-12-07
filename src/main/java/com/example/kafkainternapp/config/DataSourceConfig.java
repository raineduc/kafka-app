package com.example.kafkainternapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    private final Environment env;

    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .url(env.getProperty("db_url"))
                .username(env.getProperty("db_username"))
                .password(env.getProperty("db_password"))
                .build();
    }
}

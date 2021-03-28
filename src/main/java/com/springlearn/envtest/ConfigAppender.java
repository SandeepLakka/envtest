package com.springlearn.envtest;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.core.env.StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;

@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class ConfigAppender implements EnvironmentPostProcessor {

    private JdbcTemplate jdbcTemplate;

    private static final String DS_URL = "spring.datasource.url";
    private static final String DS_USERNAME = "spring.datasource.username";
    private static final String DS_PASSWORD = "spring.datasource.password";
    private static final String DS_DRIVER_CLASSNAME = "spring.datasource.driverClassName";



    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> params = new HashMap<>();
//        bOparamsRepository.findAll().forEach(boparams -> params.put(boparams.getName_(),boparams.getValue_()));

        jdbcTemplate = new JdbcTemplate(getDS(environment));

        List<Boparams> values = jdbcTemplate.query("select * from boparams", new ParamsMapper());
        params = values.stream().collect(
                Collectors.toMap(boparams -> boparams.getName_(), boparams -> boparams.getValue_()));

        log.info("params are {}",params);
        environment.getPropertySources()
                .addAfter(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, new MapPropertySource("boParams", params));
    }

    private DataSource getDS(ConfigurableEnvironment environment){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty(DS_DRIVER_CLASSNAME));
        dataSource.setJdbcUrl(environment.getProperty(DS_URL));
        dataSource.setUsername(environment.getProperty(DS_USERNAME));
        dataSource.setPassword(environment.getProperty(DS_PASSWORD));
        return dataSource;
    }


    class ParamsMapper implements RowMapper<Boparams> {

        @Override
        public Boparams mapRow(ResultSet resultSet, int i) throws SQLException {
            Boparams boparams = new Boparams();
            boparams.setName_(resultSet.getString("name_"));
            boparams.setValue_(resultSet.getString("value_"));
            //TODO : Need to assess if this field is necessary and also to include other fields
            /*boparams.setSecured_( (char) resultSet.getInt("secured_"));*/
            return boparams;
        }
    }

}

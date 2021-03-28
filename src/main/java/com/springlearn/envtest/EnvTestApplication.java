package com.springlearn.envtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@Slf4j
@SpringBootApplication
public class EnvTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(EnvTestApplication.class, args);

        ctx.getEnvironment().getPropertySources().
                stream().iterator().forEachRemaining(propertySource -> {
            log.info(">>: {} with {}", propertySource.getName(), propertySource.getSource());
        });
        log.info("===========All yours=====================");
    }
}

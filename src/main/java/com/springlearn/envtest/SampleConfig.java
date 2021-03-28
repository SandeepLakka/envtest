package com.springlearn.envtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleConfig {
    private final BOparamsRepository bOparamsRepository;


    @Autowired
    public SampleConfig(BOparamsRepository bOparamsRepository) {
        this.bOparamsRepository = bOparamsRepository;
    }

    @Bean
    public void setConfig(){

    }
}

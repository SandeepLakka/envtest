package com.springlearn.envtest.controller;

import com.springlearn.envtest.model.Boparams;
import com.springlearn.envtest.repository.BOparamsRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class DataController {

    //Constants
    private final static String PROP_TM_USER = "tm.user";
    private final static String PROP_TM_PASSWORD = "tm.password";
    private final static String PROP_TM_HOST = "tm.hostname";
    private final static String PROP_TM_PORT = "tm.port";

    private final JdbcTemplate jdbcTemplate;

    private final BOparamsRepository boparamsRepository;

    public DataController(JdbcTemplate jdbcTemplate, BOparamsRepository boparamsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.boparamsRepository = boparamsRepository;
    }

    @GetMapping("/create-schema")
    @ResponseBody
    public String createSchema() {
        jdbcTemplate.execute("CREATE TABLE BOPARAMS (\n" +
                "    NAME_ VARCHAR(255) NOT NULL,\n" +
                "    VALUE_ VARCHAR(255) NOT NULL,\n" +
                "    SECURED_ NUMBER(1),\n" +
                "    PRIMARY KEY (NAME_)\n" +
                ");");
        log.info("Schema population done");
        return "Schema population done";
    }

    @GetMapping("/load-data")
    @ResponseBody
    public String loadData() {
        Boparams param1, param2, param3, param4;

        if(!boparamsRepository.findById(PROP_TM_HOST).isPresent()) {
            param1 = Boparams.builder()
                    .name_(PROP_TM_HOST)
                    .value_("tmHost")
                    .build();

            boparamsRepository.save(param1);
        }

        if(!boparamsRepository.findById(PROP_TM_USER).isPresent()) {
            param2 = Boparams.builder()
                    .name_(PROP_TM_USER)
                    .value_("tmUser")
                    .build();

            boparamsRepository.save(param2);
        }

        if(!boparamsRepository.findById(PROP_TM_PASSWORD).isPresent()) {
            param3 = Boparams.builder()
                    .name_(PROP_TM_PASSWORD)
                    .value_("tmPass")
                    .secured_(true)
                    .build();

            boparamsRepository.save(param3);
        }

        if(!boparamsRepository.findById(PROP_TM_PORT).isPresent()) {
            param4 = Boparams.builder()
                    .name_(PROP_TM_PORT)
                    .value_("1234")
                    .build();

            boparamsRepository.save(param4);
        }

        log.info("Data population is done");
        return "Data population is done";
    }

    @GetMapping("/addDesert")
    @ResponseBody
    public String addDesertInDB(){
        Boparams desert = Boparams.builder()
                .name_("prepared.desert")
                .value_("Gulab Jamoon")
                .build();
        boparamsRepository.save(desert);
        return "Gulab Jamoon added as desert in DB";
    }

    @Configuration
    @ConfigurationProperties(prefix = "tm")
    @Data
    public static class TMConfig {
        private String user;
        private String password;
        private String hostname;
        private String port;
    }

}

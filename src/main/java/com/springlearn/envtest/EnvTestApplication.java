package com.springlearn.envtest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@Slf4j
@SpringBootApplication
public class EnvTestApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Value("${tm.user:dummy}")
	private String tmUser;

	@Value("${tm.password:dummyPassword}")
	private String tmPass;

	@Value("${tm.hostname:dummyHost}")
	private String tmHost;

	@Value("${tm.port:dummyPort}")
	private String tmPort;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	BOparamsRepository boparamsRepository;

	@Autowired
	TMConfig tmConfig;
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(EnvTestApplication.class, args);
		log.info("beans {}",ctx.getBeanDefinitionCount());
		StringBuilder sb = new StringBuilder();
		ctx.getBeanFactory().getBeanNamesIterator().forEachRemaining(s -> sb.append(s));
		log.info("=================================\nbeans: \n{}",sb);
		log.info("\n=================================\n");

		ctx.getEnvironment().getPropertySources().
				stream().iterator().forEachRemaining(propertySource -> {
					log.info(">>: {} with {}",propertySource.getName(),propertySource.getSource());
		});
	}

	@GetMapping("wishTMUser")
	@ResponseBody
	public String wishTMUser(){
		return "hello "+tmUser+" This is security warning: You are listening on "+tmPort+" at "+tmHost+" and also have password "+tmPass;
	}

	@GetMapping("wishHim")
	@ResponseBody
	public String wishHim(){
		return "hello "+tmConfig.getUser()+" I see your password "+tmConfig.getPassword()+" from port "+tmConfig.getPort()+" on host "+tmConfig.getHostname();
	}
	@GetMapping
	@ResponseBody
	public String getValue(){
		return "hello "+tmUser;
	}


	@GetMapping("/create-schema")
	@ResponseBody
	public String createSchema(){
		jdbcTemplate.execute("CREATE TABLE BOPARAMS (\n" +
				"    NAME_ VARCHAR(255) NOT NULL,\n" +
				"    VALUE_ VARCHAR(255) NOT NULL,\n" +
				"    SECURED_ CHAR(1) DEFAULT 'N',\n" +
				"    PRIMARY KEY (NAME_)\n" +
				");");
		jdbcTemplate.execute("commit");
		log.info("Table created");
		return "Table created";
	}

	@GetMapping("load-data")
	@ResponseBody
	public String loadData(){
		Boparams boparams1 = new Boparams();
		boparams1.setName_("tm.port");
		boparams1.setValue_("1234");

		BOparamsRepository s = boparamsRepository;
		s.save(boparams1);
		Boparams param2, param3, param4;
		param2 = new Boparams();
		param2.setName_("tm.user");
		param2.setValue_("tmUser");
		s.save(param2);

		param3 = new Boparams();
		param3.setName_("tm.password");
		param3.setValue_("tmPass");
		param3.setSecured_('Y');
		s.save(param3);

		param4 = new Boparams();
		param4.setName_("tm.hostname");
		param4.setValue_("tmHost");
		s.save(param4);
		log.info("Data loaded");
		return "Data loaded";
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

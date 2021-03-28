package com.springlearn.envtest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WishController {


    @Value("${tm.user:dummy}")
    private String tmUser;
    @Value("${tm.password:dummyPassword}")
    private String tmPass;
    @Value("${tm.hostname:dummyHost}")
    private String tmHost;
    @Value("${tm.port:dummyPort}")
    private String tmPort;

    private final DataController.TMConfig tmConfig;

    public WishController(DataController.TMConfig tmConfig) {
        this.tmConfig = tmConfig;
    }

    @GetMapping("wish1")
    @ResponseBody
    public String wishValueAnnotationTMUser() {
        return "hello " + tmUser + " This is security warning: You are listening on " +
                tmPort + " at " + tmHost + " and also have password " + tmPass;
    }

    @GetMapping("wish2")
    @ResponseBody
    public String wishConfigurationPropertiesAnnotationTMUser() {
        return "hello " + tmConfig.getUser() + " I see your password " + tmConfig.getPassword() +
                " from port " + tmConfig.getPort() + " on host " + tmConfig.getHostname();
    }

    @Value("${prepared.desert:None}")
    private String desertPrepared;

    @GetMapping("wish3")
    @ResponseBody
    public String wishConfigurationWithOverridingValueInAppProperties(){
        return "Desert prepared is "+desertPrepared;
    }

}

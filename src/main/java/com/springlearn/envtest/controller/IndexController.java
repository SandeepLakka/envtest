package com.springlearn.envtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping
    @ResponseBody
    public String help(){

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------HELP---------------------\n\r");
        sb.append("This Application tries to inject custom properties\n");
        sb.append("from custom sources like Database.\n\n");
        sb.append("Check if Table BOPARAMS exists by checking /h2-console\n");
        sb.append("if Table doesn't exist, fire /create-schema endpoint\n");
        sb.append("If table doesn't have entries with tm.* properties in name_\n");
        sb.append("Then fire /load-data endpoint\n");
        sb.append("If schema and data are loaded with endpoints, then restart app.\n");
        sb.append("Check DB property (name_) and its values(value_) in BOPARAMS table.\n");
        sb.append("checking endpoints /wish1, /wish2 should show the values from DB.\n");
        sb.append("** Note: DB values overrides the application properties\n");
        sb.append("To validate this: fire endpoint /addDesert to create property 'prepared.desert'\n");
        sb.append("and restart application. Now as we have property 'prepared.desert' in both\n");
        sb.append("application.properties and in DB as well. value from DB is picked up and shown when we fire endpoint /wish3\n");
        sb.append("You can also check the application log for more info on the properties sourced from DB with source name 'boParams'\n");
        sb.append("\n---------------------~HELP~---------------------\n");
        return sb.toString();
    }
}

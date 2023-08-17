package com.itniuma.controller;

import com.itniuma.MyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {
    @Value("${user}")
    private String user;

    @Value("${hobbies[1]}")
    private String hobby;

    @Value("${friends[0].name}")
    private String friendName;

    @Autowired
    private Environment environment;

    @Autowired
    private MyDataSource myDataSource;

    @GetMapping
    public String getRequest() {
        System.out.println("accept get request");
        System.out.println(this.environment.getProperty("baseDir"));
        System.out.println(myDataSource);
        return String.format("accept get request\t" +
                        "username: %s\t" +
                        "hobby:%s\t" +
                        "friendName:%s"
                , this.user, this.hobby, this.friendName);
    }
}

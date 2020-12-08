package com.david.module;

import com.david.module.util.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@RestController
public class TestRestController {

    @RequestMapping("/hello")
    public String helloWord(){
        Person person = new Person();
        return "hello world: david \n";
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println(2);
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println(2);
    }
}

package com.dclasyc.springboot.firstrestapi.helloworld;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
public class helloWorldResource {
    @RequestMapping("/hello-world")
    public String hello(){
        return "Hello World";
    }

    @RequestMapping("/hello-world-param/{name}/message/{message}")
    public HelloWorldBean helloWorldBean(
            @PathVariable String name,
            @PathVariable String message){
        return new HelloWorldBean ("Hello World, "+ name +", "+message);
    }
}

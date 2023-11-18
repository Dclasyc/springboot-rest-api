package com.dclasyc.springboot.firstrestapi.helloworld;

public class HelloWorldBean {

    public HelloWorldBean(String message) {
        this.message = message;
    }

    private String message;


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HelloWorldBean{" +
                "message='" + message + '\'' +
                '}';
    }
}

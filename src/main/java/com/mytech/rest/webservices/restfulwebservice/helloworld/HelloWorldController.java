package com.mytech.rest.webservices.restfulwebservice.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    HelloWorldController(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @GetMapping("/hello-world")
    public String getHelloWorldString(){
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean getHelloWorldBean(){
        return new HelloWorldBean("Hello World") ;
    }

    @GetMapping("/hello-world/path-variable/{name}")
    public HelloWorldBean getHelloWorldPathVariable(@PathVariable String name){
        return  new HelloWorldBean(String.format("Hello World, %s",name ));
    }

    @GetMapping("/hello-world-internationalized")
    public String getHelloWorldInternationalized(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message",null, "Default Message",locale);
        //return "Hello World";
    }
}

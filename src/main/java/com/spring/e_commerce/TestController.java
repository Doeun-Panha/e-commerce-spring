package com.spring.e_commerce;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String sayHello(){
        return "Hello Panha! Your Spring server is working perfectly.";
    }
}

package com.example.securityproject.controllers;

import com.example.securityproject.models.User;
import com.example.securityproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "app")
public class AppController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String sayHelloWorld() {
        return "Hello world";
    }

    @GetMapping("/user")
    public @ResponseBody User sayUser(@RequestBody User user) {
        return userService.save(user);
    }
}

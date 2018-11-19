package com.bytemates.demo.controller;

import com.bytemates.demo.model.User;
import com.bytemates.demo.repository.UserRepository;
import com.bytemates.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/ping")
    public void ping(){

        userService.saveDummyUser();
        System.out.println("pinged");
    }

    @GetMapping("/get-all")
    public List<User> getUsers(){

        return userRepository.findAll();
    }
}

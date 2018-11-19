package com.bytemates.demo.service;

import com.bytemates.demo.model.User;
import com.bytemates.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveDummyUser(){
        User dummyUser = new User();
        dummyUser.setName("dummy name");
        dummyUser.setPassportNumber("");
        userRepository.save(dummyUser);
    }
}

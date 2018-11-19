package com.bytemates.demo.controller;

import com.bytemates.demo.model.User;
import com.bytemates.demo.repository.UserRepository;
import com.bytemates.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/ping")
    public void ping() throws IOException {

        userService.saveDummyUser();
        System.out.println("pinged");
    }

    @GetMapping("/get-all")
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        return userRepository.findById(id).get();
    }

    @GetMapping("/stream-signature/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getUserSignature(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok().header(CONTENT_DISPOSITION, "attachment : filename=signature")
                .contentType(MediaType.parseMediaType(user.getSignatureExtension()))
                .body(new ByteArrayResource(user.getSignature()));

    }

}

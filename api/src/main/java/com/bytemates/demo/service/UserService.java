package com.bytemates.demo.service;

import com.bytemates.demo.model.User;
import com.bytemates.demo.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveDummyUser() throws IOException {
        User dummyUser = new User();
        dummyUser.setName("client-1");
        dummyUser.setEmail("dummy@gmail.com");
        dummyUser.setContactNumber(773882);

        File signature = new ClassPathResource("static/sample.png").getFile();
        dummyUser.setSignature(Files.readAllBytes(signature.toPath()));
        dummyUser.setSignatureExtension("image/png");

        userRepository.save(dummyUser);
    }
}

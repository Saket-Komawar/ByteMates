package com.bytemates.demo.service;

import com.bytemates.demo.model.DocumentType;
import com.bytemates.demo.model.User;
import com.bytemates.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Transactional
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

    public void uploadDocument(Long id, MultipartFile file, DocumentType documentType) throws IOException {
        User user = userRepository.findById(id).get();
        byte[] content = file.getBytes();
        String fileExtension = file.getContentType();
        switch (documentType) {
            case PASSPORT:
                user.setPassport(content);
                user.setPassportExtension(fileExtension);
                break;
            case IPQ:
                user.setIpq(content);
                user.setIpqExtension(fileExtension);
                break;

            case ADDRESS:
                user.setAdderss(content);
                user.setAddressExtension(fileExtension);
                break;
            default:
                user.setSignature(content);
                user.setSignatureExtension(fileExtension);
        }

        //not required
        userRepository.save(user);
    }
}

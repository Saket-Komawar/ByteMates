package com.bytemates.demo.service;

import com.bytemates.demo.model.DocumentType;
import com.bytemates.demo.model.User;
import com.bytemates.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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

    @Autowired
    ToPDFService toPDFService;

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
        byte[] content = ToPDFService.getByteArray(file);
        String fileExtension = file.getContentType();
        switch (documentType) {
            case PASSPORT:
                user.setPassportDocument(content);
                user.setPassportExtension(fileExtension);
                break;
            case IPQ:
                user.setIpqDocument(content);
                user.setIpqExtension(fileExtension);
                break;

            case ADDRESS:
                user.setAddressDocument(content);
                user.setAddressExtension(fileExtension);
                break;
            default:
                user.setSignature(content);
                user.setSignatureExtension(fileExtension);
        }

        //not required
        userRepository.save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createTestData() throws IOException {
        System.out.println("hello world, creating test data");
        userRepository.save(populateByteArrayInUser(1));
        userRepository.save(populateByteArrayInUser(2));
        userRepository.save(populateByteArrayInUser(3));
        System.out.println("test data created");
    }

    private User populateByteArrayInUser(int id) throws IOException {

        User dummyuser = new User();
        dummyuser.setName("client-" + id);
        dummyuser.setContactNumber(Integer.parseInt("777777" + id));
        dummyuser.setEmail("dummymail" + id + "@gmail.com");
        dummyuser.setPassportNumber("L31541" + id);
        dummyuser.setAddress("EON - " + id + " Credit Suisse, Pune");

        String pathPrefix = "static/client-" + id + "/";

        dummyuser.setSignature(Files.readAllBytes(new ClassPathResource(pathPrefix + "signature.pdf").getFile().toPath()));
        dummyuser.setAddressDocument(Files.readAllBytes(new ClassPathResource(pathPrefix + "address.pdf").getFile().toPath()));
        dummyuser.setIpqDocument(Files.readAllBytes(new ClassPathResource(pathPrefix + "IPQ.pdf").getFile().toPath()));
        dummyuser.setPassportDocument(Files.readAllBytes(new ClassPathResource(pathPrefix + "passport.pdf").getFile().toPath()));

        dummyuser.setSignatureExtension("application/pdf");
        dummyuser.setAddressExtension("application/pdf");
        dummyuser.setPassportExtension("application/pdf");
        dummyuser.setIpqExtension("application/pdf");

        return dummyuser;
    }
}

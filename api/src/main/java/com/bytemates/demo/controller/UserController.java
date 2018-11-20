package com.bytemates.demo.controller;

import com.bytemates.demo.model.DocumentType;
import com.bytemates.demo.model.User;
import com.bytemates.demo.repository.UserRepository;
import com.bytemates.demo.service.UserService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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
    public void ping() {

        //userService.saveDummyUser();
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

    @GetMapping("/stream-document/{id}/{documentType}")
    @ResponseBody
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id, @PathVariable DocumentType documentType) {
        User user = userRepository.findById(id).get();
        byte[] content;
        String documentExtension;
        switch (documentType) {
            case PASSPORT:
                content = user.getPassportDocument();
                documentExtension = user.getPassportExtension();
                break;
            case IPQ:
                content = user.getIpqDocument();
                documentExtension = user.getIpqExtension();
                break;

            case ADDRESS:
                content = user.getAddressDocument();
                documentExtension = user.getAddressExtension();
                break;
            default:
                content = user.getSignature();
                documentExtension = user.getSignatureExtension();
        }

        return ResponseEntity.ok().header(CONTENT_DISPOSITION, "attachment; filename=" + documentType.toString())
                .contentType(MediaType.parseMediaType(documentExtension))
                .body(new ByteArrayResource(content));

    }

    //should ideally be put
    @PostMapping("/upload-document/{id}/{documentType}")
    public ResponseEntity uploadDocument(@PathVariable Long id, @PathVariable DocumentType documentType,
                                         @RequestParam("file") MultipartFile file) throws IOException, DocumentException {

        userService.uploadDocument(id, file, documentType);
        return ResponseEntity.ok().body("document uploaded");
    }

    @PostMapping("sign-document/{id}/{documentType}")
    public void signDocument(@RequestBody String encodedString, @PathVariable Long id, @PathVariable DocumentType documentType) throws Exception {

        String encoded = encodedString.split(",")[1];
        byte[] decoded = Base64.getDecoder().decode(encoded);
        userService.signDocument(id, documentType, decoded);

    }
}

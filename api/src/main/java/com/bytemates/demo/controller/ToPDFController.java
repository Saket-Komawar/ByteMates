package com.bytemates.demo.controller;

import com.bytemates.demo.service.ToPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toPdf")
public class ToPDFController {
    @Autowired
    private ToPDFService toPDFService;

    @GetMapping("/image")
    public void fromImage(){
        String filename = "JackMa1";
        String extension = "png";
        toPDFService.generatePDFFromImage(filename, extension);
    }
}

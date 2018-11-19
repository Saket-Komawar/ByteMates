package com.bytemates.demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Document {

    //todo : relationship one to one relationship with user

    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private byte[] passport;
    private String passportExtension;

    @Lob
    private byte[] ipq;
    private String ipqExtension;

    @Lob
    private byte[] adderss;
    private String addressExtension;


}

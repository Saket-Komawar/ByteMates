package com.bytemates.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private int contactNumber;

    //blob
    @Lob
    private byte[] signature;
    private String signatureExtension;

    @Lob
    private byte[] passportDocument;
    private String passportExtension;
    private String passportNumber;

    @Lob
    private byte[] ipqDocument;
    private String ipqExtension;

    @Lob
    private byte[] addressDocument;
    private String addressExtension;
    private String address;


}

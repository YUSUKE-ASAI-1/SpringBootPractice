package com.example.demo.data;

import lombok.Data;

@Data
public class ContactData {
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String zipCode;
    private String address;
    private String buildingName;
    private String contactType;
    private String body;
}
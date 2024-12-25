package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Contact;
import com.example.demo.form.AdminSignupForm;

public interface AdminService {
    void saveAdmin(AdminSignupForm adminsignupform);
    boolean existsByEmail(String email);
    Admin findByEmail(String email); 
    boolean authenticate(String email, String password);
    public List<Contact> getAllContacts();
	Contact getContactById(Long id);}
    
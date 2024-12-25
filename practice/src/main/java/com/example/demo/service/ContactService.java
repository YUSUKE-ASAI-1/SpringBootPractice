package com.example.demo.service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;

public interface ContactService {

    void saveContact(ContactForm contactForm);

	Contact findById(Long id);

	void save(Contact contact);

	void delete(Long id);

	Contact getContactById(Long id);

}

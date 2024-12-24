package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void saveContact(ContactForm contactForm) {
		Contact contact = new Contact();

		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());

		contactRepository.save(contact);
	}

	@Override
	public Contact findById(Long id) {
		Optional<Contact> contactOptional = contactRepository.findById(id);
		if (contactOptional.isPresent()) {
			return contactOptional.get();
		}
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void save(Contact contact) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void delete(Long id) {
	        contactRepository.deleteById(id);
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public Contact getContactById(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}

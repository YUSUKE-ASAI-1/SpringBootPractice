package com.example.demo.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Contact;
import com.example.demo.form.AdminSignupForm;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.ContactRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private BCryptPasswordEncoder BCryptPasswordEncoder;

	@Override
	public void saveAdmin(AdminSignupForm adminSignupForm) {

		if (adminRepository.existsByEmail(adminSignupForm.getEmail())) {
			throw new IllegalArgumentException("このメールアドレスは既に登録されています");
		}

		Admin admin = new Admin();
		admin.setLastName(adminSignupForm.getLastName());
		admin.setFirstName(adminSignupForm.getFirstName());
		admin.setEmail(adminSignupForm.getEmail());
		admin.setPassword(BCryptPasswordEncoder.encode(adminSignupForm.getPassword()));

		adminRepository.save(admin);
	}

	@Override
	public boolean existsByEmail(String email) {
		return adminRepository.existsByEmail(email);
	}

	@Override
	public Admin findByEmail(String email) {
		return adminRepository.findByEmail(email).orElse(null);
	}

	@Override
	public boolean authenticate(String email, String password) {
		Admin admin = findByEmail(email);
		if (admin == null) {
			return false;
		}
		return BCryptPasswordEncoder.matches(password, admin.getPassword());
	}

	private final ContactRepository contactRepository;

	public AdminServiceImpl(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	@Override
	public Contact getContactById(Long id) {
	    return contactRepository.findById(id).orElse(null);
	}
}
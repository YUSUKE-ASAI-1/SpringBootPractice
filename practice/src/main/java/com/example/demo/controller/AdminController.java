package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Contact;
import com.example.demo.form.AdminSigninForm;
import com.example.demo.form.AdminSignupForm;
import com.example.demo.form.ContactForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.ContactService;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ContactService contactService;


    @GetMapping("/admin/signup")
    public String signup(Model model) {
        model.addAttribute("adminSignupForm", new AdminSignupForm());
        return "/admin/signup";
    }

    @PostMapping("/admin/signup")
    public String signup(@Validated @ModelAttribute("adminSignupForm") AdminSignupForm adminSignupForm,
            BindingResult errorResult, HttpServletRequest request) {

        if (errorResult.hasErrors()) {
            return "/admin/signup";
        }

        HttpSession session = request.getSession();
        session.setAttribute("adminSignupForm", adminSignupForm);

        return "redirect:/admin/signupconfirmation";
    }

    @GetMapping("/admin/signupconfirmation")
    public String signupconfirmation(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        AdminSignupForm adminSignupForm = (AdminSignupForm) session.getAttribute("adminSignupForm");

        if (adminSignupForm == null) {
            return "redirect:/admin/signup";
        }

        model.addAttribute("adminSignupForm", adminSignupForm);
        return "/admin/signupconfirmation";
    }

    @PostMapping("/admin/register")
    public String register(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        AdminSignupForm adminSignupForm = (AdminSignupForm) session.getAttribute("adminSignupForm");

        if (adminSignupForm == null) {
            return "redirect:/admin/signup";
        }

        adminService.saveAdmin(adminSignupForm);

        return "redirect:/admin/signin";
    }

    @GetMapping("/admin/signin")
    public String signin(Model model) {
        model.addAttribute("adminSigninForm", new AdminSigninForm());
        return "/admin/signin";
    }

    @PostMapping("/admin/signin")
    public String signin(@Validated @ModelAttribute("adminSigninForm") AdminSigninForm adminSigninForm,
            BindingResult errorResult, Model model, HttpServletRequest request) {


        if (errorResult.hasErrors()) {
            return "/admin/signin";
        }

        boolean isAuthenticated = adminService.authenticate(adminSigninForm.getEmail(), adminSigninForm.getPassword());

        if (!isAuthenticated) {
            model.addAttribute("loginError", "メールアドレスまたはパスワードが正しくありません");
            return "/admin/signin";
        }

        Admin admin = adminService.findByEmail(adminSigninForm.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                admin.getEmail(), admin.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);  

        return "redirect:/admin/contacts";
    }

    @GetMapping("/admin/contacts")
    public String showContacts(Model model) {

           List<Contact> contacts = adminService.getAllContacts();
            model.addAttribute("contacts", contacts);

            return "/admin/contacts";
    }
    @GetMapping("/admin/contacts/{id}")
    public String showContactDetail(@PathVariable Long id, Model model) {
        Contact contact = adminService.getContactById(id); 
        if (contact == null) {
            return "redirect:/admin/contacts"; 
        }
        model.addAttribute("contact", contact);
        return "admin/contactDetail"; 
    }

    @GetMapping("/admin/contacts/{id}/edit")
    public String editContact(@PathVariable Long id, Model model) {
        
        Contact contact = contactService.findById(id);
        
        if (contact == null) {
            return "redirect:/admin/contacts"; 
        }
        
        ContactForm contactForm = new ContactForm();
        contactForm.setLastName(contact.getLastName());
        contactForm.setFirstName(contact.getFirstName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhone(contact.getPhone());
        contactForm.setZipCode(contact.getZipCode());
        contactForm.setAddress(contact.getAddress());
        contactForm.setBuildingName(contact.getBuildingName());
        contactForm.setContactType(contact.getContactType());
        contactForm.setBody(contact.getBody());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", id);

        return "admin/contact_edit_form";
    }
    
    @Transactional
    @PostMapping("/admin/contacts/{id}/edit")
    public String updateContact(@PathVariable Long id, 
                                @Validated @ModelAttribute ContactForm contactForm, 
                                BindingResult bindingResult, 
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("contactId", id);
            return "admin/contact_edit_form";
        }

        Contact contact = contactService.findById(id);
        if (contact == null) {

            System.out.println("お問い合わせが見つかりませんでした。リダイレクトします。");
            return "redirect:/admin/contacts"; 
        }

        contact.setLastName(contactForm.getLastName());
        contact.setFirstName(contactForm.getFirstName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setZipCode(contactForm.getZipCode());
        contact.setAddress(contactForm.getAddress());
        contact.setBuildingName(contactForm.getBuildingName());
        contact.setContactType(contactForm.getContactType());
        contact.setBody(contactForm.getBody());

        System.out.println("Saving updated contact: " + contact);
        contactService.save(contact);

        return "redirect:/admin/contacts/" + id;
    }

    @DeleteMapping("/admin/contacts/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.delete(id);
        return "redirect:/admin/contacts";
    }

    @GetMapping("/admin/logout")
    public String logout() {
        return "redirect:/admin/signin";
    }
}
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.data.ContactData;

@Controller
public class ContactController {
    @PostMapping("/contact")
    public ModelAndView contact(@ModelAttribute ContactData contactData, ModelAndView mv) {

        mv.setViewName("confirmation");

        mv.addObject("lastName", contactData.getLastName());
        mv.addObject("firstName", contactData.getFirstName());
        mv.addObject("email", contactData.getEmail());
        mv.addObject("phone", contactData.getPhone());
        mv.addObject("zipCode", contactData.getZipCode());
        mv.addObject("address", contactData.getAddress());
        mv.addObject("buildingName", contactData.getBuildingName());
        mv.addObject("contactType", contactData.getContactType());
        mv.addObject("body", contactData.getBody());

        return mv;
    }
}

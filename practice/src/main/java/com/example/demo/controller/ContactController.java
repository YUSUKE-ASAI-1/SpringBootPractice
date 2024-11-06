package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {
    @PostMapping("/contact")
    public ModelAndView contact(@RequestParam("lastName") String lastName,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("email") String email,
                                @RequestParam("phone") String phone,
                                @RequestParam("zipCode") String zipCode,
                                @RequestParam("address") String address,
                                @RequestParam("buildingName") String buildingName,
                                @RequestParam("contactType") String contactType,
                                @RequestParam("body") String body,
                                ModelAndView mv) {

        mv.setViewName("confirmation");

        mv.addObject("lastName", lastName);
        mv.addObject("firstName", firstName);
        mv.addObject("email", email);
        mv.addObject("phone", phone);
        mv.addObject("zipCode", zipCode);
        mv.addObject("address", address);
        mv.addObject("buildingName", buildingName);
        mv.addObject("contactType", contactType);
        mv.addObject("body", body);

        return mv;
    }
}
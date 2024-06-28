package org.example.techblog.controllers;
import org.example.techblog.dtos.contactdtos.ContactCreateDto;
import org.example.techblog.dtos.contactdtos.ContactDto;
import org.example.techblog.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;
    @GetMapping("/admin/contact")
    public String contact(Model model){
        List<ContactDto> contacts = contactService.getAllContact();
        model.addAttribute("contacts", contacts);
        return "/dashboard/contact/contactingUser";
    }
    @GetMapping("/contact")
    public String addContact(){
        return "/contact-create";
    }

    @PostMapping("/contact/create")
    public String addContact(@ModelAttribute ContactCreateDto contactCreateDto){
        contactService.add(contactCreateDto);
        return "redirect:/contact";
    }

    @PostMapping("/contact")
    public String register(ContactDto contactDto){
        boolean res = contactService.register(contactDto);
        if (res==false) {
            return "contact-create";
        }
        return "redirect:contact";
    }

    @GetMapping("/contact/confirm")
    public String confirm(String email) {
        boolean res =  contactService.confirmEmail(email);
        return "redirect:/contact";
    }
}

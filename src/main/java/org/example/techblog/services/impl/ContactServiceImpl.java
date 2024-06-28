package org.example.techblog.services.impl;

import org.example.techblog.dtos.contactdtos.ContactCreateDto;
import org.example.techblog.dtos.contactdtos.ContactDto;
import org.example.techblog.models.Contact;
import org.example.techblog.repositories.ContactRepository;
import org.example.techblog.services.ContactService;
import org.example.techblog.services.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Override
    public void add(ContactCreateDto contactCreateDto) {
        Contact contact = modelMapper.map(contactCreateDto, Contact.class);
        contact.setSendDate(new Date());
        contactRepository.save(contact);

        // Send email with contact details
        emailService.sendContactFormEmail(
                contact.getEmail(),
                contact.getFullName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getSubject(),
                contact.getMessage()
        );
    }


    @Override
    public List<ContactDto> getAllContact() {
        List<ContactDto> contacts = contactRepository.findAll()
                .stream()
                .map(contact -> modelMapper.map(contact, ContactDto.class))
                .collect(Collectors.toList());
        return contacts;
    }

    @Override
    public boolean register(ContactDto contact) {
        Contact contactingUser = contactRepository.findByEmail(contact.getEmail());
        if (contactingUser != null) {
            return false;
        }

        Contact newContactingUser = modelMapper.map(contact, Contact.class);
        newContactingUser.setEmail(contact.getEmail());

        contactRepository.save(newContactingUser);
        emailService.sendConfirmationEmail(contact.getEmail());
        return true;
    }
    @Override
    public boolean confirmEmail(String email) {
        Contact findUser = contactRepository.findByEmail(email);
        if (findUser != null) {
            contactRepository.save(findUser);
            return true;
        }
        return false;
    }
}

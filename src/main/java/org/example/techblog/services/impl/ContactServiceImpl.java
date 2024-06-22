package org.example.techblog.services.impl;

import org.example.techblog.dtos.contactdtos.ContactCreateDto;
import org.example.techblog.dtos.contactdtos.ContactDto;
import org.example.techblog.models.Contact;
import org.example.techblog.repositories.ContactRepository;
import org.example.techblog.services.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void add(ContactCreateDto contactCreateDto) {
        Contact contact = modelMapper.map(contactCreateDto, Contact.class);
        contact.setSendDate(new Date());
        contactRepository.save(contact);
    }

    @Override
    public List<ContactDto> getAllContact() {
        List<ContactDto> contacts = contactRepository.findAll()
                .stream()
                .map(contact -> modelMapper.map(contact, ContactDto.class))
                .collect(Collectors.toList());
        return contacts;
    }
}

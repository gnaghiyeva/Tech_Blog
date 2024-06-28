package org.example.techblog.services;
import org.example.techblog.dtos.articledtos.ArticleHomeDto;
import org.example.techblog.dtos.contactdtos.ContactCreateDto;
import org.example.techblog.dtos.contactdtos.ContactDto;

import java.util.List;

public interface ContactService {
    void add(ContactCreateDto ContactCreateDto);

    List<ContactDto> getAllContact();

    boolean register(ContactDto contactDto);
    boolean confirmEmail(String email);
    List<ContactDto> getRecentEnter();

}

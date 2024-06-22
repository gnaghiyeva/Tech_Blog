package org.example.techblog.dtos.contactdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactCreateDto {
    private String fullName;
    private String email;
    private String phone;
    private String subject;
    private String message;
//    private Date sendDate;
}

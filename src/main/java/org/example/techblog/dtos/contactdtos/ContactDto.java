package org.example.techblog.dtos.contactdtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContactDto {
    private String fullName;
    private String email;
    private String phone;
    private String subject;
    private String message;
//    private Date sendDate;
}

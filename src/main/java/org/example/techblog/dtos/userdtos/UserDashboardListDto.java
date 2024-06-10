package org.example.techblog.dtos.userdtos;

import lombok.Getter;
import lombok.Setter;
import org.example.techblog.models.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDashboardListDto {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private Boolean emailConfirmed;
    private List<Role> roles = new ArrayList<>();
}

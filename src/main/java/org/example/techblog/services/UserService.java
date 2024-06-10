package org.example.techblog.services;

import org.example.techblog.dtos.authdtos.RegisterDto;


public interface UserService {
    boolean register(RegisterDto registerDto);
    boolean confirmEmail(String email, String token);
}

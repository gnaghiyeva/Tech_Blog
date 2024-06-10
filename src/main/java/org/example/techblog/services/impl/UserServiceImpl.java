package org.example.techblog.services.impl;

import org.example.techblog.dtos.authdtos.RegisterDto;
import org.example.techblog.models.UserEntity;
import org.example.techblog.repositories.UserRepository;
import org.example.techblog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public boolean register(RegisterDto registerDto) {
        UserEntity user = userRepository.findByEmail(registerDto.getEmail());
        if (user != null) {
            return false;
        }
        String hashPassword = bCryptPasswordEncoder.encode(registerDto.getPassword());
        Random random = new Random();
//      String token = String.valueOf(random.nextInt(26,30));
        String token = bCryptPasswordEncoder.encode(registerDto.getEmail());
        UserEntity newUser = modelMapper.map(registerDto, UserEntity.class);
        newUser.setEmailConfirmed(false);
        newUser.setConfirmationToken(token);
        newUser.setPassword(hashPassword);
        userRepository.save(newUser);
        return true;
    }
    }


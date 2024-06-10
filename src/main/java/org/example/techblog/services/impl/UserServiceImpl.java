package org.example.techblog.services.impl;
import org.example.techblog.dtos.authdtos.RegisterDto;
import org.example.techblog.models.UserEntity;
import org.example.techblog.repositories.UserRepository;
import org.example.techblog.services.EmailService;
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

    @Autowired
    private EmailService emailService;

    @Override
    public boolean register(RegisterDto register) {
        UserEntity user = userRepository.findByEmail(register.getEmail());
        if (user != null) {
            return false;
        }
        String hashPassword = bCryptPasswordEncoder.encode(register.getPassword());
        Random random = new Random();
//      String token = String.valueOf(random.nextInt(26,30));
        String token = bCryptPasswordEncoder.encode(register.getEmail());
        UserEntity newUser = modelMapper.map(register, UserEntity.class);
        newUser.setEmailConfirmed(false);
        newUser.setConfirmationToken(token);
        newUser.setPassword(hashPassword);
        userRepository.save(newUser);
        emailService.sendConfirmationEmail(register.getEmail(), token);
        return true;
    }

    @Override
    public boolean confirmEmail(String email, String token) {
        UserEntity findUser = userRepository.findByEmail(email);
        if(findUser.getConfirmationToken().equals(token) && findUser!=null){
            findUser.setEmailConfirmed(true);
            userRepository.save(findUser);
            return true;
        }
        return false;
    }
}





package org.example.techblog.services;

import org.example.techblog.dtos.authdtos.RegisterDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.userdtos.UserAddRoleDto;
import org.example.techblog.dtos.userdtos.UserDashboardListDto;
import org.example.techblog.dtos.userdtos.UserDto;

import java.util.List;


public interface UserService {
    boolean register(RegisterDto registerDto);
    boolean confirmEmail(String email, String token);

    List<UserDashboardListDto> getDashboardUsers();

    UserDto getUserById(Long id);
    void addRole(UserAddRoleDto userAddRole);
    List<UserDto> getAllUsers();

    long countUsers();
}

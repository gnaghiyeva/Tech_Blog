package org.example.techblog.controllers;

import org.example.techblog.dtos.categorydtos.CategoryCreateDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.roledtos.RoleDto;
import org.example.techblog.dtos.userdtos.UserAddRoleDto;
import org.example.techblog.dtos.userdtos.UserDashboardListDto;
import org.example.techblog.dtos.userdtos.UserDto;
import org.example.techblog.services.CategoryService;
import org.example.techblog.services.RoleService;
import org.example.techblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @GetMapping("/admin")
    public String index() {

        return "/dashboard/home";
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        List<UserDashboardListDto> userList = userService.getDashboardUsers();
        model.addAttribute("users",userList);
        return "/dashboard/auth/user-list";
    }

    @GetMapping("/admin/users/role/{id}")
    public String addRole(@PathVariable Long id, Model model) {
        List<RoleDto> roles = roleService.getRoles();
        UserDto user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "/dashboard/auth/user-role";
    }

    @PostMapping("/admin/users/addrole")
    public String addRole(UserAddRoleDto addRoleDto)
    {
        userService.addRole(addRoleDto);
        return "/dashboard/auth/user-list";
    }

    @GetMapping("/admin/admin-info")
    public String getAdminInfo(Model model) {
        List<UserDashboardListDto> userList = userService.getDashboardUsers();
        model.addAttribute("users",userList);
        return "/dashboard/admin-info";
    }


}

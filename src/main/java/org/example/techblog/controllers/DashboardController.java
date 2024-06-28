package org.example.techblog.controllers;

import org.example.techblog.dtos.articledtos.ArticleHomeDto;
import org.example.techblog.dtos.categorydtos.CategoryCreateDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.contactdtos.ContactDto;
import org.example.techblog.dtos.roledtos.RoleDto;
import org.example.techblog.dtos.userdtos.UserAddRoleDto;
import org.example.techblog.dtos.userdtos.UserDashboardListDto;
import org.example.techblog.dtos.userdtos.UserDto;
import org.example.techblog.services.*;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ContactService contactService;
    @GetMapping("/admin")
    public String index(Model model) {
        List<CategoryDto> homeCategories = categoryService.getAllCategories();
        List<ArticleHomeDto> homeArticles = articleService.getHomeArticles();
        model.addAttribute("categories", homeCategories);
        model.addAttribute("articles", homeArticles);

        long countCategories = homeCategories.stream().count();
        model.addAttribute("countCategories", countCategories);

        long countArticles = homeArticles.stream().count();
        model.addAttribute("countArticles", countArticles);

        long countUsers = userService.countUsers();
        model.addAttribute("userCount", countUsers);

        List<ContactDto> recentContacts = contactService.getRecentEnter();
        model.addAttribute("recentContacts", recentContacts);

        List<ContactDto> contacts = contactService.getAllContact();
        model.addAttribute("contacts", contacts);
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

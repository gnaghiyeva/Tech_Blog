package org.example.techblog.controllers;

import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class NavbarController {
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("navItems")
    public List<CategoryDto> getNavItems() {
      return  categoryService.getAllCategories();
    }

}

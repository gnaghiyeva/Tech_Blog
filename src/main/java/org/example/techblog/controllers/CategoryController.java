package org.example.techblog.controllers;

import org.example.techblog.dtos.categorydtos.CategoryCreateDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin")
    public String index() {

        return "/dashboard/home";
    }

    @GetMapping("/admin/category")
    public String category(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/dashboard/category/category";
    }

    @GetMapping("/admin/category/category-create")
    public String addCategory() {
        return "/dashboard/category/category-create";
    }

    @PostMapping("/admin/category/create")
    public String addCategory(@ModelAttribute CategoryCreateDto categoryCreateDto) {
        categoryService.add(categoryCreateDto);
        return "redirect:/admin/category";
    }
}



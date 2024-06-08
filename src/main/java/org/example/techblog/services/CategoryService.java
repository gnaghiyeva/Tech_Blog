package org.example.techblog.services;

import org.example.techblog.dtos.categorydtos.CategoryCreateDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;

import java.util.List;

public interface CategoryService {
    void add(CategoryCreateDto categoryCreateDto);

    List<CategoryDto> getAllCategories();
}

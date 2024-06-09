package org.example.techblog.services;

import org.example.techblog.dtos.categorydtos.CategoryCreateDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.categorydtos.CategoryUpdateDto;

import java.util.List;

public interface CategoryService {
    void add(CategoryCreateDto categoryCreateDto);

    List<CategoryDto> getAllCategories();

    void removeCategory(Long categoryId);

    void updateCategory(CategoryUpdateDto categoryDto);


    CategoryUpdateDto findUpdatedCategory(Long id);
}

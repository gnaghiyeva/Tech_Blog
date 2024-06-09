package org.example.techblog.services.impl;

import org.example.techblog.dtos.categorydtos.CategoryCreateDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.models.Category;
import org.example.techblog.repositories.CategoryRepository;
import org.example.techblog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired(required = true)
    private ModelMapper modelMapper;

    @Override
    public void add(CategoryCreateDto categoryCreateDto) {
        Category category = modelMapper.map(categoryCreateDto, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categoryDtoList = categoryRepository.findAll()
                .stream()
                .filter(x->x.getIsDeleted()==false)
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public void removeCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
       category.setIsDeleted(true);
//        categoryRepository.delete(category);
        categoryRepository.save(category);
    }
}

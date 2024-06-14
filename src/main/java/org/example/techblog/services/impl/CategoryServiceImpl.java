package org.example.techblog.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.techblog.dtos.categorydtos.CategoryCreateDto;
import org.example.techblog.dtos.categorydtos.CategoryDto;
import org.example.techblog.dtos.categorydtos.CategoryUpdateDto;
import org.example.techblog.models.Article;
import org.example.techblog.models.Category;
import org.example.techblog.repositories.ArticleRepository;
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

    @Autowired
    private ArticleRepository articleRepository;

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
        List<Article> articles = articleRepository.findByCategoryId(categoryId);
        for (Article article : articles) {
            article.setIsDeleted(true);
            articleRepository.save(article);
        }
        categoryRepository.save(category);
    }


//    @Override
//    public void updateCategory(CategoryUpdateDto categoryDto) {
//        Category findCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow();
//        findCategory.setName(categoryDto.getName());
//        categoryRepository.saveAndFlush(findCategory);
//    }
//
//    @Override
//    public CategoryUpdateDto findUpdatedCategory(Long id) {
//        Category category = categoryRepository.findById(id).orElseThrow();
//        CategoryUpdateDto categoryUpdateDto = modelMapper.map(category, CategoryUpdateDto.class);
//        return categoryUpdateDto;
//    }

    @Override
    public void updateCategory(CategoryUpdateDto categoryDto) {
        if (categoryDto == null || categoryDto.getId() == null) {
            throw new IllegalArgumentException("Category or Category ID cannot be null");
        }

        Category findCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        findCategory.setName(categoryDto.getName());
        categoryRepository.saveAndFlush(findCategory);
    }

    @Override
    public CategoryUpdateDto findUpdatedCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        CategoryUpdateDto categoryUpdateDto = modelMapper.map(category, CategoryUpdateDto.class);
        return categoryUpdateDto;
    }



}

package com.kharkitecture.backoffice.service;

import com.kharkitecture.backoffice.dao.CategoryDAO;
import com.kharkitecture.backoffice.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public void save(Category newCategory) {
        categoryDAO.save(newCategory);
    }

}
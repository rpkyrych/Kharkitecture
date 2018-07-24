package com.kharkitecture.backoffice.service;

import com.kharkitecture.backoffice.dao.CategoryDAO;
import com.kharkitecture.backoffice.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<Category> getCategories() {
        return categoryDAO.findAll();
    }

    public Category getCategory(Long id) throws ClassNotFoundException {
        Optional<Category> optionalCategory = categoryDAO.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            throw new ClassNotFoundException("Category with such id not found!");
        }
    }

    public void update(Category editedCategory) {
        categoryDAO.save(editedCategory);
    }

    public void save(Category newCategory) {
        categoryDAO.save(newCategory);
    }

    public void remove(long id) {
        categoryDAO.deleteById(id);
    }
}
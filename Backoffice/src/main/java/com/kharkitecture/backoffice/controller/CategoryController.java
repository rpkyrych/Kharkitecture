package com.kharkitecture.backoffice.controller;

import com.kharkitecture.backoffice.entity.Category;
import com.kharkitecture.backoffice.service.CategoryService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private static final String CATEGORY_PAGE = "addCategory";
    private static final Marker DB_MARKER = MarkerManager.getMarker("DATABASE");
    private CategoryService categoryService;
    private Logger logger;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.logger = LogManager.getLogger(this.getClass());
    }

    @RequestMapping(value = "/", method = GET)
    public String getCategoryPage(Model model) {
        model.addAttribute("category", new Category());
        return CATEGORY_PAGE;
    }

    @RequestMapping(value = "/all", method = GET)
    public String getCategoryList(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return CATEGORY_PAGE;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public String getCategory(@PathVariable long id, Model model) {
        try {
            model.addAttribute("category", categoryService.getCategory(id));
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, DB_MARKER, "Finding category in db exception.", e);
        }
        return CATEGORY_PAGE;
    }

    @RequestMapping(value = "/", method = POST)
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/" + CATEGORY_PAGE;
    }

    @RequestMapping(value = "/", method = PUT)
    public String updateCategory(Category editedCategory) {
        categoryService.update(editedCategory);
        return "redirect:/" + CATEGORY_PAGE;
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public String removeCategory(@PathVariable long id) {
        categoryService.remove(id);
        return "redirect:/" + CATEGORY_PAGE;
    }


}
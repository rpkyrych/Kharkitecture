package com.kharkitecture.backoffice.controller;

import com.kharkitecture.backoffice.entity.Category;
import com.kharkitecture.backoffice.service.CategoryService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping(value = "/", method = POST)
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/" + CATEGORY_PAGE;
    }


}
package com.kharkitecture.backoffice.controller;

import com.kharkitecture.backoffice.entity.Building;
import com.kharkitecture.backoffice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/building")
public class BuildingController {
    private static final String BUILDING_PAGE = "/building";
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/", method = GET)
    public String getBuildingsList(Model model) {
        model.addAttribute("buildings", bookService.getBuildings());
        return BUILDING_PAGE;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public String getBuilding(@PathVariable long id, Model model) {
        model.addAttribute("building", bookService.getBuilding(id));
        return BUILDING_PAGE;
    }

    @RequestMapping(value = "/", method = POST)
    public String addBuilding(Building newBuilding) {
        bookService.add(newBuilding);
        return "redirect:" + BUILDING_PAGE;
    }

    @RequestMapping(value = "/", method = PUT)
    public String updateBuilding(Building editedBuilding) {
        bookService.update(editedBuilding);
        return "redirect:" + BUILDING_PAGE;
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public String removeBuilding(@PathVariable long id) {
        bookService.remove(id);
        return "redirect:" + BUILDING_PAGE;
    }


}

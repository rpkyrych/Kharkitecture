package com.kharkitecture.backoffice.controller;

import com.kharkitecture.backoffice.entity.Building;
import com.kharkitecture.backoffice.service.BuildingService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/building")
public class BuildingController {
    private static final String BUILDING_PAGE = "addObject";
    private static final Marker DB_MARKER = MarkerManager.getMarker("DATABASE");
    private BuildingService buildingService;
    private Logger logger;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
        this.logger = LogManager.getLogger(this.getClass());
    }

    @RequestMapping(value = "/", method = GET)
    public String getBuildingPage(Model model) {
        model.addAttribute("building", new Building());
        return BUILDING_PAGE;
    }

    @RequestMapping(value = "/all", method = GET)
    public String getBuildingsList(Model model) {
        model.addAttribute("buildings", buildingService.getBuildings());
        return BUILDING_PAGE;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public String getBuilding(@PathVariable long id, Model model) {
        try {
            model.addAttribute("building", buildingService.getBuilding(id));
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, DB_MARKER, "Finding building in db exception.", e);
        }
        return BUILDING_PAGE;
    }

    @RequestMapping(value = "/", method = POST)
    public String addBuilding(@ModelAttribute("building") Building building) {
        buildingService.save(building);
        return "redirect:/" + BUILDING_PAGE;
    }

    @RequestMapping(value = "/", method = PUT)
    public String updateBuilding(Building editedBuilding) {
        buildingService.update(editedBuilding);
        return "redirect:/" + BUILDING_PAGE;
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public String removeBuilding(@PathVariable long id) {
        buildingService.remove(id);
        return "redirect:/" + BUILDING_PAGE;
    }


}

package com.kharkitecture.backoffice.controller;

import com.kharkitecture.backoffice.entity.Building;
import com.kharkitecture.backoffice.service.BuildingService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/building")
public class BuildingController {
    private static final String ADD_BUILDING_PAGE = "addObject";
    private static final String VIEW_ALL_BUILDINGS_PAGE = "allBuildings";
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
        return ADD_BUILDING_PAGE;
    }

    @RequestMapping(value = "/all", method = GET)
    public ModelAndView getBuildingsList() {
        ModelAndView modelAndView = new ModelAndView(VIEW_ALL_BUILDINGS_PAGE);
        List<Building> buildings = buildingService.getBuildings();
        modelAndView.addObject("buildings", buildings);
        return modelAndView;
    }

    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long buildingId) throws ClassNotFoundException {
        byte[] imageContent = buildingService.getBuilding(buildingId).getPhotos().get(0).getSmallSize();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public String getBuilding(@PathVariable long id, Model model) {
        try {
            model.addAttribute("building", buildingService.getBuilding(id));
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, DB_MARKER, "Finding building in db exception.", e);
        }
        return ADD_BUILDING_PAGE;
    }

    @RequestMapping(value = "/", method = POST)
    public String addBuilding(@ModelAttribute("building") Building building, @RequestParam("images[]") MultipartFile[] images) {
        buildingService.save(building, images);
        return "redirect:/building/";
    }

    @RequestMapping(value = "/", method = PUT)
    public String updateBuilding(Building editedBuilding) {
        buildingService.update(editedBuilding);
        return "redirect:/building/";
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public String removeBuilding(@PathVariable long id) {
        buildingService.remove(id);
        return "redirect:/building/";
    }


}

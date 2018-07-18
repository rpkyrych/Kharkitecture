package com.kharkitecture.backoffice.service;

import com.kharkitecture.backoffice.dao.BuildingDAO;
import com.kharkitecture.backoffice.entity.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BuildingDAO buildingDAO;

    @Autowired
    public BookService(BuildingDAO buildingDAO) {
        this.buildingDAO = buildingDAO;
    }

    public List<Building> getBuildings() {
        return buildingDAO.findAll();
    }

    public Building getBuilding(long id) {
        Optional<Building> optionalBuilding = buildingDAO.findById(id);
        if (optionalBuilding.isPresent()) {
            return optionalBuilding.get();
        }
        return null;
    }

    public void update(Building editedBuilding) {
        buildingDAO.save(editedBuilding);
    }

    public void save(Building newBuilding) {
        buildingDAO.save(newBuilding);
    }

    public void remove(long id) {
        buildingDAO.deleteById(id);
    }
}

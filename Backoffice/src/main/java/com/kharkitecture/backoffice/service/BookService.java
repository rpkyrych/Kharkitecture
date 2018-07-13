package com.kharkitecture.backoffice.service;

import com.kharkitecture.backoffice.dao.BuildingDAO;
import com.kharkitecture.backoffice.entity.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    @Qualifier("hibernate")
    private BuildingDAO buildingDAO;

    public List<Building> getBuildings() {
        return buildingDAO.getBuildingsList();
    }

    public Building getBuilding(long id) {
        return buildingDAO.get(id);
    }

    public void update(Building editedBuilding) {
        buildingDAO.update(editedBuilding);
    }

    public void add(Building newBuilding) {
        buildingDAO.add(newBuilding);
    }

    public void remove(long id) {
        buildingDAO.remove(id);
    }
}

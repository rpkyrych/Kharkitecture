package com.kharkitecture.backoffice.dao;

import com.kharkitecture.backoffice.entity.Building;

import java.util.List;

public interface BuildingDAO {
    void add(Building building);

    Building get(long id);

    void update(Building editedBuilding);

    void remove(long id);

    boolean isBuildingExists(long id);

    List<Building> getBuildingsList();
}

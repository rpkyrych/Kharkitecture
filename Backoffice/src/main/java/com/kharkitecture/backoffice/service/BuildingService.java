package com.kharkitecture.backoffice.service;

import com.kharkitecture.backoffice.dao.BuildingDAO;
import com.kharkitecture.backoffice.entity.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {
    private BuildingDAO buildingDAO;
    private PhotoService photoService;

    @Autowired
    public BuildingService(BuildingDAO buildingDAO, PhotoService photoService) {
        this.buildingDAO = buildingDAO;
        this.photoService = photoService;
    }

    public List<Building> getBuildings() {
        return buildingDAO.findAll();
    }

    public Building getBuilding(long id) throws ClassNotFoundException {
        return Optional.of(buildingDAO.findById(id)).map(Optional::get).orElseThrow(ClassNotFoundException::new);
    }

    public void update(Building editedBuilding) {
        buildingDAO.save(editedBuilding);
    }

    public void save(Building newBuilding, MultipartFile[] images) {
        if (images != null) {
            photoService.addResizedPhotosToBuilding(newBuilding, images);
        }
        buildingDAO.save(newBuilding);
    }

    public void remove(long id) {
        buildingDAO.deleteById(id);
    }
}

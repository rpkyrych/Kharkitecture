package com.kharkitecture.backoffice.dao;

import com.kharkitecture.backoffice.entity.Building;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingDAO extends CrudRepository<Building, Long> {
    Optional<Building> findById(Long id);

    Building save(Building building);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Building> findAll();
}

package com.kharkitecture.backoffice.dao;

import com.kharkitecture.backoffice.entity.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoDAO extends CrudRepository<Photo, Long>
{
    Optional<Photo> findById(Long id);

    Photo save(Photo building);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Photo> findAll();
}

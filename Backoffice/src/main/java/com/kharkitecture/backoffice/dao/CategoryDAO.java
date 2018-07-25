package com.kharkitecture.backoffice.dao;

import com.kharkitecture.backoffice.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryDAO extends CrudRepository<Category, Long>
{
    Optional<Category> findById(Long id);

    Category save(Category category);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Category> findAll();
}

package com.kharkitecture.backoffice.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.EAGER, cascade ={CascadeType.ALL})
    @JoinColumn(name = "building_id")
    private List<Photo> photos;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "category_building",
            joinColumns = {@JoinColumn(name = "building_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")
            })
    private List<Category> categories;

    public Building() {
    }

    public Building(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Building(String name, String description, List<Photo> photos) {
        this.name = name;
        this.description = description;
        this.photos = photos;
    }

    public Building(String name, String description, List<Photo> photos, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.photos = photos;
        this.categories = categories;
    }

    public boolean addCategory(Category category) {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories.add(category);
    }

    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Building)) return false;
        Building building = (Building) o;
        return id.equals(building.id) &&
                Objects.equals(name, building.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

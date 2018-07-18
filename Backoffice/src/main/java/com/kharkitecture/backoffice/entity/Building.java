package com.kharkitecture.backoffice.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Photo.class)
    private List<Photo> photos;
    @ManyToMany(targetEntity = Category.class)
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

    public boolean addPhotoURL(Photo url) {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        return photos.add(url);
    }

    public boolean addCategory(Category category) {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories.add(category);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return photos != null ? photos : Collections.emptyList();
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
        return id == building.id &&
                Objects.equals(name, building.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}

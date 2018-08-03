package com.kharkitecture.backoffice.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo")
    private long id;
    private String image;
    @ManyToOne(targetEntity = Building.class)
    private Building building;

    public Photo() {
    }

    public Photo(String image) {
        this.image = image;
    }

    public Photo(String image, Building building) {
        this.image = image;
        this.building = building;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;
        Photo photo = (Photo) o;
        return id == photo.id &&
                Objects.equals(image, photo.image);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, image);
    }
}

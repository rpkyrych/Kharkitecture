package com.kharkitecture.backoffice.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo")
    private long id;
    @ManyToOne(targetEntity = Building.class)
    private Building building;
    @Column(name = "original_size")
    byte[] originalSize;
    @Column(name = "small_size")
    byte[] smallSize;
    @Column(name = "middle_size")
    byte[] middleSize;
    @Column(name = "large_size")
    byte[] largeSize;

    public Photo() {
    }
    public Photo(Building building, byte[] originalSize) {
        this.building = building;
        this.originalSize = originalSize;
    }

    public Photo(byte[] originalSize) {
        this.originalSize = originalSize;
    }

    public byte[] getOriginalSize() {
        return originalSize;
    }

    public void setOriginalSize(byte[] originalSize) {
        this.originalSize = originalSize;
    }

    public byte[] getSmallSize() {
        return smallSize;
    }

    public void setSmallSize(byte[] smallSize) {
        this.smallSize = smallSize;
    }

    public byte[] getMiddleSize() {
        return middleSize;
    }

    public void setMiddleSize(byte[] middleSize) {
        this.middleSize = middleSize;
    }

    public byte[] getLargeSize() {
        return largeSize;
    }

    public void setLargeSize(byte[] largeSize) {
        this.largeSize = largeSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id == photo.id &&
                Arrays.equals(originalSize, photo.originalSize);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(originalSize);
        return result;
    }
}

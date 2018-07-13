package com.kharkitecture.backoffice.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    private static long nextId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String characteristic;
    @ManyToMany(targetEntity = Building.class)
    private List<Building> buildingList;

    public Category(String characteristic) {
        this.id = ++nextId;
        this.characteristic = characteristic;
    }

    public Category(long id, String characteristic) {
        this.id = id;
        this.characteristic = characteristic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public List<Building> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<Building> buildingList) {
        this.buildingList = buildingList;
    }
}

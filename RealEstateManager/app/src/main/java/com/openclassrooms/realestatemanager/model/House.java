package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity
public class House {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "house_name")
    @NonNull
    private String name;
    @ColumnInfo(name ="price")
    @NonNull
    private String price;
    @ColumnInfo(name = "available")
    @NonNull
    private boolean available;
    @ColumnInfo(name = "agent_id")
    @NonNull
    private String AgentId;
    @ColumnInfo(name = "monetary_system")
    @NonNull
    private String monetarySystem;
    @ColumnInfo(name = "measure_system")
    @NonNull
    private String measureUnity;


    public House(String id, String name, String price, boolean available, String monetarySystem, String measureUnity) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.available = available;
        this.monetarySystem = monetarySystem;
        this.measureUnity = measureUnity;
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonetarySystem() {
        return monetarySystem;
    }

    public void setMonetarySystem(String monetarySystem) {
        this.monetarySystem = monetarySystem;
    }

    public String getMeasureUnity() {
        return measureUnity;
    }

    public void setMeasureUnity(String measureUnity) {
        this.measureUnity = measureUnity;
    }
}

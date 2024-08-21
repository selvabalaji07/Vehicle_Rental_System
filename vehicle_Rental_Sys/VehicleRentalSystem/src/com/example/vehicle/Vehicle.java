package com.example.vehicle;

public class Vehicle {
    private int id;
    private String model;
    private String type;
    private double rentPerDay;
    private int availableCount;

    public Vehicle(int id, String model, String type, double rentPerDay, int availableCount) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.rentPerDay = rentPerDay;
        this.availableCount = availableCount;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public double getRentPerDay() {
        return rentPerDay;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    @Override
    public String toString() {
        return "Vehicle [id=" + id + ", model=" + model + ", type=" + type + ", rentPerDay=" + rentPerDay + ", availableCount=" + availableCount + "]";
    }
}


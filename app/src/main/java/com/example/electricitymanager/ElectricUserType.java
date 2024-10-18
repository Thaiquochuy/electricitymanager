package com.example.electricitymanager;

public class ElectricUserType{
    private int id;
    private String name;
    private int unitPrice;

    public ElectricUserType(int id, String name, int unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return " (ID: " + id + ")" + " " + name + " - " + unitPrice ;  // Customize the format as needed
    }

}

package com.example.electricitymanager;

import java.io.Serializable;

public class Customer implements Serializable {

    private int id;
    private String name;
    private String dob;
    private String address;
    private int usedElectricNum;
    private String electricTypeName;
    private double unitPrice;

    public Customer(int id, String name, String dob, String address, int usedElectricNum, String electricTypeName, double unitPrice) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.usedElectricNum = usedElectricNum;
        this.electricTypeName = electricTypeName;
        this.unitPrice = unitPrice;
    }


    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public int getUsedElectricNum() {
        return usedElectricNum;
    }

    public String getElectricTypeName() {
        return electricTypeName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUsedElectricNum(int usedElectricNum) {
        this.usedElectricNum = usedElectricNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

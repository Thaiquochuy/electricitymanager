package com.example.electricitymanager;

import java.io.Serializable;

public class Customer implements Serializable {

    private int id;  // ID của khách hàng
    private String name;  // Tên của khách hàng
    private String dob;  // Ngày sinh của khách hàng
    private String address;  // Địa chỉ của khách hàng
    private final int usedElectricNum;  // Số điện đã sử dụng
    private final String electricTypeName;  // Tên loại người dùng điện
    private final double unitPrice;  // Đơn giá điện

    // Constructor
    public Customer(int id, String name, String dob, String address, int usedElectricNum, String electricTypeName, double unitPrice) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.usedElectricNum = usedElectricNum;
        this.electricTypeName = electricTypeName;
        this.unitPrice = unitPrice;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}

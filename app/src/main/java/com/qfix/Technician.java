package com.qfix;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Technician class
 */
public class Technician implements Serializable {
    public Technician() {

    }

    private String name, businessName,
            repairService, location,
            email, phone,userID;
    private Bitmap dp;
    private int stars;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Bitmap getDp() {
        return dp;
    }

    public Technician(String name, String repairService) {
        this.name = name;
        this.repairService = repairService;
    }

    public void setDp(Bitmap dp) {
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getRepairService() {
        return repairService;
    }

    public void setRepairService(String repairService) {
        this.repairService = repairService;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Technician{" +
                "name='" + name + '\'' +
                ", businessName='" + businessName + '\'' +
                ", repairService='" + repairService + '\'' +
                ", location='" + location + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userID='" + userID + '\'' +
                ", dp=" + dp +
                ", stars=" + stars +
                '}';
    }
}

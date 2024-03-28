package com.example.sitoapplication.model;

import java.util.Date;
import com.google.firebase.Timestamp;

public class User {
    public String name;
    public String phoneNumber;
    public String dateOfBirth;
    public String address;

    public User() {
    }

    public User(String name, String phoneNumber, String dateOfBirth, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
//    public void setDateOfBirth(Timestamp timestamp) {
//        this.dateOfBirth = timestamp.toDate();
//    }
//
//    // Chuyển đổi java.util.Date thành Timestamp
//    public Timestamp dateToTimestamp() {
//        return new Timestamp(dateOfBirth);
//    }
}

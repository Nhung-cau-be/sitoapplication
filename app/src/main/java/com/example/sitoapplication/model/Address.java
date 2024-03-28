package com.example.sitoapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Address {
    private String street;
    private String wardName;
    private String districtName;
    private String provinceName;

    public Address() {
    }

    public Address(String street, String wardName, String districtName, String provinceName) {
        this.street = street;
        this.wardName = wardName;
        this.districtName = districtName;
        this.provinceName = provinceName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String displayAddress() {
        List<String> addressArrays = new ArrayList<>();
        if(this.street != null && !this.street.isBlank())
            addressArrays.add(this.street);
        if(this.wardName != null && !this.wardName.isBlank())
            addressArrays.add(this.wardName);
        if(this.districtName != null && !this.districtName.isBlank())
            addressArrays.add(this.districtName);
        if(this.provinceName != null && !this.provinceName.isBlank())
            addressArrays.add(this.provinceName);

        return String.join(", ", addressArrays);
    }
}

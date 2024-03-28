package com.example.sitoapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Address {
    private String street;
    private String wardCode;
    private String wardName;
    private String districtCode;
    private String districtName;
    private String provinceCode;
    private String provinceName;

    public Address() {
    }

    public Address(String street, String wardCode, String wardName, String districtCode, String districtName, String provinceCode, String provinceName) {
        this.street = street;
        this.wardCode = wardCode;
        this.wardName = wardName;
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
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

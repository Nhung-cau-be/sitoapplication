package com.example.sitoapplication.model;

import java.util.List;

public class Province {
    private String code;
    private String name;
    private String type;
    private List<District> districts;

    public Province() {
    }

    public Province(String code, String name, String type, List<District> districts) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.districts = districts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}

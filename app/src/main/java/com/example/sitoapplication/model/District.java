package com.example.sitoapplication.model;

import java.util.List;

public class District {
    private String code;
    private String name;
    private String type;
    private List<Ward> wards;

    public District() {
    }

    public District(String code, String name, String type, List<Ward> wards) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.wards = wards;
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

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }
}

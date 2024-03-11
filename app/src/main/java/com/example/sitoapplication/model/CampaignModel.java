package com.example.sitoapplication.model;

import java.util.Date;

import lombok.Data;
import lombok.Getter;

@Data
public class CampaignModel {
    @Getter
    private String id;
    private String name;
    private Long target;
    private Date deadline;
    private String address;
    private String story;
}

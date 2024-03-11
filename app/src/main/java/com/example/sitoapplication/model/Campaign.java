package com.example.sitoapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Campaign {
    @PrimaryKey
    @ColumnInfo
    private String id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private Long target;
    @ColumnInfo
    private Date deadline;
    @ColumnInfo
    private String address;
    @ColumnInfo
    private String story;
}

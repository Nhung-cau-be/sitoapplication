package com.example.sitoapplication.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.sitoapplication.database.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "campaign")
@TypeConverters(DateConverter.class)
public class Campaign {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    public long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}

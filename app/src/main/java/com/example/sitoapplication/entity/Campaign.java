package com.example.sitoapplication.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "campaign")
public class Campaign {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    public long id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private Long target;
    @ColumnInfo
    private Long deadline;
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

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
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

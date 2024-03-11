package com.example.sitoapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sitoapplication.model.Campaign;

import java.util.List;

@Dao
public interface CampaignDao {
    @Query("SELECT * FROM Campaign")
    List<Campaign> getAll();

    @Query("SELECT * FROM Campaign c WHERE c.id = :id")
    Campaign getById(String id);

    @Insert
    boolean insert(List<Campaign> campaigns);
}

package com.example.sitoapplication.repository.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sitoapplication.entity.Campaign;

import java.util.List;

@Dao
public interface CampaignDao {
    @Query("SELECT * FROM Campaign")
    LiveData<List<Campaign>> getAll();

    @Query("SELECT * FROM Campaign c WHERE c.id = :id")
    Campaign getById(String id);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Campaign campaign);
}

package com.example.sitoapplication.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sitoapplication.database.entity.Campaign;

import java.util.List;

@Dao
public interface CampaignDao {
    @Query("SELECT * FROM Campaign")
    LiveData<List<Campaign>> getAll();

    @Query("SELECT * FROM Campaign c WHERE c.id = :id")
    Campaign getById(String id);

    @Query("SELECT * FROM Campaign c WHERE c.id = :campaignId")
    LiveData<Campaign> getByCampaignId (String campaignId);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Campaign campaign);
}

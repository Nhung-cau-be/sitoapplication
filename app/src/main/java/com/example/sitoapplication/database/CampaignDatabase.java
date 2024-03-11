package com.example.sitoapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sitoapplication.database.dao.CampaignDao;
import com.example.sitoapplication.model.Campaign;

@Database(entities = {Campaign.class}, version = 1)
public abstract class CampaignDatabase extends RoomDatabase {
    public abstract CampaignDao campaignDao();
}
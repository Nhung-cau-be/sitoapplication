package com.example.sitoapplication.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sitoapplication.repository.database.dao.CampaignDao;
import com.example.sitoapplication.entity.Campaign;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Campaign.class}, version = 1)
public abstract class CampaignDatabase extends RoomDatabase {
    public abstract CampaignDao campaignDao();

    private static volatile CampaignDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static CampaignDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CampaignDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CampaignDatabase.class, "campaign")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
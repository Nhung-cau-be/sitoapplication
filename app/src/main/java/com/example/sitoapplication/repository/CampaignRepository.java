package com.example.sitoapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sitoapplication.repository.database.AppDatabase;
import com.example.sitoapplication.repository.database.dao.CampaignDao;
import com.example.sitoapplication.entity.Campaign;

import java.util.List;

public class CampaignRepository {
    private CampaignDao mCampaignDao;
    private LiveData<List<Campaign>> mAllCampaigns;

    public CampaignRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mCampaignDao = db.campaignDao();
        mAllCampaigns = mCampaignDao.getAll();
    }

    public LiveData<List<Campaign>> getAll() {
        return mAllCampaigns;
    }

    public void insert(Campaign campaign) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCampaignDao.insert(campaign);
        });
    }
}

package com.example.sitoapplication.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sitoapplication.entity.Campaign;
import com.example.sitoapplication.repository.CampaignRepository;

import java.util.List;

public class CampaignViewModel extends AndroidViewModel {
    private CampaignRepository mRepository;

    private final LiveData<List<Campaign>> mAllCampaigns;

    public CampaignViewModel(Application application) {
        super(application);
        mRepository = new CampaignRepository(application);
        mAllCampaigns = mRepository.getAll();
    }

    public LiveData<List<Campaign>> getAll() {
        return mAllCampaigns;
    }

    public void insert(Campaign campaign) {
        mRepository.insert(campaign);
    }

    public LiveData<Campaign> getById(String campaignId) {
        return mRepository.getByCampaignId(campaignId);
    }
}

package com.example.sitoapplication.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeSearchViewModel extends ViewModel {
    private final MutableLiveData<String> searchString = new MutableLiveData<String>();
    public void setSearchString(String newSearchString) {
        searchString.setValue(newSearchString);
    }
    public LiveData<String> getSearchString() {
        return searchString;
    }
}

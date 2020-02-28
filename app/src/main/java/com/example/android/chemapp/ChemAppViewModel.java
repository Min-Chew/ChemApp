package com.example.android.chemapp;

import com.example.android.chemapp.data.ChemElement;
import com.example.android.chemapp.data.ChemElementAttributes;
import com.example.android.chemapp.data.Status;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ChemAppViewModel extends ViewModel {
    private ChemElementAttributes mElement;
    private LiveData<List<ChemElement>> mSearchResults;
    private LiveData<Status> mLoadingStatus;

    public ChemAppViewModel() {
        mElement = new ChemElementAttributes();
        mSearchResults = mElement.getSearchResults();
        mLoadingStatus = mElement.getLoadingStatus();
    }

    public void loadSearchResults(String query) {
        mElement.loadSearchResults(query);
    }

    public LiveData<List<ChemElement>> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}

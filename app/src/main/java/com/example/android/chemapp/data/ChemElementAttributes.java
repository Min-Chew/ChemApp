package com.example.android.chemapp.data;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.chemapp.utils.ChemElementUtils;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ChemElementAttributes implements ChemElementAsyncTask.Callback {
    private static final String TAG = ChemElementAttributes.class.getSimpleName();
    private MutableLiveData<List<ChemElement>> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;

    private String mCurrentQuery;

    public ChemElementAttributes() {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<List<ChemElement>> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    @Override
    public void onSearchFinished(List<ChemElement> searchResults) {
        System.out.println("In ChemElementUtils.java");
        for(int i=0;i<searchResults.size();i++){
            System.out.println(searchResults.get(i));
        }

        mSearchResults.setValue(searchResults);

        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, mCurrentQuery);
    }

    public void loadSearchResults(String query) {
        if (shouldExecuteSearch(query)) {
            mCurrentQuery = query;
            String url = ChemElementUtils.buildElementSearchURL();
            mSearchResults.setValue(null);
            Log.d(TAG, "executing search with url: " + url);
            mLoadingStatus.setValue(Status.LOADING);
            new ChemElementAsyncTask(this).execute(url);
        } else {
            Log.d(TAG, "using cached search results: " + mCurrentQuery);
        }
    }
}

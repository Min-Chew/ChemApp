package com.example.android.chemapp.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.chemapp.utils.ChemElementUtils;
import com.example.android.chemapp.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ChemElementAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;

    public interface Callback {
        void onSearchFinished(List<ChemElement> searchResults);
    }

    public ChemElementAsyncTask(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String searchResults = null;
        try {
            searchResults = NetworkUtils.doHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    @Override
    protected void onPostExecute(String s) {
        List<ChemElement> searchResults = null;
        if (s != null) {
            searchResults = ChemElementUtils.parseElementSearchResults(s);
            Log.d(TAG, "RETURNED JSON" + searchResults);
        }
        mCallback.onSearchFinished(searchResults);
    }
}

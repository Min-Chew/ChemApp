package com.example.android.chemapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.android.chemapp.data.ChemElement;
import com.example.android.chemapp.data.Status;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ElementSearchAdapter.OnSearchResultClickListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private List<ChemElement> results;
    private RecyclerView mSearchResultsRV;
    private EditText mSearchBoxET;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mErrorMessageTV;
    private DrawerLayout mDrawerLayout;

    private ElementSearchAdapter mElementSearchAdapter;

    private ChemAppViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu);

        mSearchBoxET = findViewById(R.id.et_search_box);
        mSearchResultsRV = findViewById(R.id.rv_search_results);

        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRV.setHasFixedSize(true);

        mElementSearchAdapter = new ElementSearchAdapter(this);
        mSearchResultsRV.setAdapter(mElementSearchAdapter);

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTV = findViewById(R.id.tv_error_message);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mViewModel = new ViewModelProvider(this).get(ChemAppViewModel.class);

        mViewModel.getSearchResults().observe(this, new Observer<List<ChemElement>>() {
            @Override
            public void onChanged(List<ChemElement> ChemElements) {
                results = ChemElements;
                //repo.name = "donut";
                mElementSearchAdapter.updateSearchResults(ChemElements);
            }
        });

        mViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.VISIBLE);
                    mErrorMessageTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.INVISIBLE);
                    mErrorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        });

        getPeriodicTable("dummy_text"); //passing trash because nothing is being done to the passed variable
//        Button searchButton = findViewById(R.id.btn_search);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { //this on click is not needed as far as I can tell
//                String searchQuery = mSearchBoxET.getText().toString();
//                if (!TextUtils.isEmpty(searchQuery)) {
//                    onQueryTextChange(searchQuery);
//                }
//            }
//        });

        mSearchBoxET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final StringBuilder sb = new StringBuilder(s.length());
                sb.append(s);
                    onQueryTextChange(sb.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_search:
                return true;
            case R.id.nav_saved_repos:
                Intent savedReposIntent = new Intent(this, SavedReposActivity.class);
                startActivity(savedReposIntent);
                return true;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onSearchResultClicked(ChemElement repo) {
        Intent intent = new Intent(this, ElementDetailActivity.class);
        intent.putExtra(ElementDetailActivity.EXTRA_CHEM_ELEMENT, repo);
        //Log.d(TAG, "onSearchResultClicked: " + repo.name);
        startActivity(intent);
    }

    private void getPeriodicTable(String searchQuery) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mViewModel.loadSearchResults(searchQuery);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange");
        String userInput = newText.toLowerCase();
        List<ChemElement> newList = new ArrayList<>();

        if(newText.equals("")) {
            //do nothing
            /*for (int i = 0; i < results.size(); i++) {
                newList.add(results.get(i));
            }*/
        }
        else {
            for (int i = 0; i < results.size(); i++) {
                String AtmNum = Integer.toString(results.get(i).atomicNumber);
                if (results.get(i).name.toLowerCase().contains(userInput)) {
                    newList.add(results.get(i));
                } else if (AtmNum.contains(userInput)) {
                    newList.add(results.get(i));
                }
            }
        }
        mElementSearchAdapter.updateList(newList);
        return false;
    }
}

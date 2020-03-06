package com.example.android.chemapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.chemapp.data.ChemElement;

import java.util.List;

public class ElementDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CHEM_ELEMENT = "ChemElement";
    private static final String TAG = "ElementDetailActivity";

    private ChemElement mRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_CHEM_ELEMENT)) {
            mRepo = (ChemElement)intent.getSerializableExtra(EXTRA_CHEM_ELEMENT);

            TextView repoNameTV = findViewById(R.id.tv_element_name);
            repoNameTV.setText(mRepo.name);

            TextView repoStarsTV = findViewById(R.id.tv_element_atomic_num);
            repoStarsTV.setText(Integer.toString(mRepo.atomicNumber));

            TextView repoDescriptionTV = findViewById(R.id.tv_element_atomic_radius);
            repoDescriptionTV.setText(mRepo.atomicRadius);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_wiki:
                viewWikiOnWeb();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void viewWikiOnWeb() {
        if (mRepo != null) {
            Uri repoUri = Uri.parse("https://en.wikipedia.org/wiki/" + mRepo.name);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, repoUri);

            PackageManager pm = getPackageManager();
            List<ResolveInfo> activities = pm.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (activities.size() > 0) {
                startActivity(webIntent);
            }
        }
    }
}

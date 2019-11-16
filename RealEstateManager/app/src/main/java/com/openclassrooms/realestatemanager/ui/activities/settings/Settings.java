package com.openclassrooms.realestatemanager.ui.activities.settings;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.db.dao.preferences.PreferencesDao;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.settings.SettingsAdapter;

public class Settings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SettingsAdapter settingsAdapter;
    private RealEstateManagerAPIService service = DI.getService();
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.settings_rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        settingsAdapter = new SettingsAdapter(this);
        recyclerView.setAdapter(settingsAdapter);
        service.setActivity(this, "Settings");

    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        image = SettingsAdapter.getImageTypeClick();
        if (requestCode == 100) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 90);
        }

        if (requestCode == 90) {
            Uri imageUri = data.getData();
            if (image == "User") {
                service.getPreferences().setUserPhoto(imageUri.toString());
            } else if (image == "Menu"){
               service.getPreferences().setMenuImage(imageUri.toString());
            }
            settingsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
            return super.onOptionsItemSelected(item);
    }

}

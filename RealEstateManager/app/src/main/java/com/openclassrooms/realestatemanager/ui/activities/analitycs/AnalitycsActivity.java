package com.openclassrooms.realestatemanager.ui.activities.analitycs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.analitycs.AnalitycsAdapter;

public class AnalitycsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RealEstateManagerAPIService service = DI.getService();
    private SaveToDatabase database = SaveToDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analitycs);

        Toolbar toolbar = findViewById(R.id.toolbar_analitycs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Analitycs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service.setActivity(this, "Analytics");

        recyclerView = findViewById(R.id.analytics_rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (service.getMyHouses().size() > 0) {
            AnalitycsAdapter adapter = new AnalitycsAdapter(service.getMyHouses(), database.houseDao().getHouses());
            recyclerView.setAdapter(adapter);
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

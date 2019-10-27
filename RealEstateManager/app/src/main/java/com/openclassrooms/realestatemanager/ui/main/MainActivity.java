package com.openclassrooms.realestatemanager.ui.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.ConnectionFragment;
import com.openclassrooms.realestatemanager.ui.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment(new MainFragment(), "MainFrag" );

        Handler loadingHandler = new Handler();
        loadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeFragment(new ConnectionFragment(), "ConnectionFrag");
            }
        }, 3000);
    }

    private void changeFragment(Fragment fragment, String value){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, value).addToBackStack(value).commit();
    }

}

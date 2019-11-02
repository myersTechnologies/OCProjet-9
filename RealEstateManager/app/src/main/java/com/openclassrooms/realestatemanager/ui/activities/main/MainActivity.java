package com.openclassrooms.realestatemanager.ui.activities.main;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.main.ConnectionFragment;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment(new ConnectionFragment(), "ConnectionFrag");

    }

    private void changeFragment(Fragment fragment, String value){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, value).addToBackStack(value).commit();
    }

}

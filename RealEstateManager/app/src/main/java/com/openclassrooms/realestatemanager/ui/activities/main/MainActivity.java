package com.openclassrooms.realestatemanager.ui.activities.main;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.fragments.main.ConnectionFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        RealEstateManagerAPIService service = DI.getService();

        service.setActivity(this, "Main");

        final FirebaseHelper helper = DI.getFirebaseDatabase();
        service.setUsers(helper.getUsersFromFireBase());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        helper.getHouseFromFirebase();
                        helper.getDetailsFromFireBase();
                        helper.getPhotosFromFirebase();
                        helper.getHousesAdressesFromFirebase();
                    }
                });

            }
        };
       Thread thread = new Thread(runnable);
       thread.start();
       runnable.run();

       changeFragment(new ConnectionFragment(), "ConnectionFrag");


    }

    private void changeFragment(Fragment fragment, String value){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, value).addToBackStack(value).commit();
    }

}

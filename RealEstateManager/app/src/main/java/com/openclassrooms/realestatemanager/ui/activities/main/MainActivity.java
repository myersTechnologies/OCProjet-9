package com.openclassrooms.realestatemanager.ui.activities.main;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.fragments.main.ConnectionFragment;

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

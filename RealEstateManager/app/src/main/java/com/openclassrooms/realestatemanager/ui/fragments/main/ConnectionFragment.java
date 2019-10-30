package com.openclassrooms.realestatemanager.ui.fragments.main;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.second.SecondActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionFragment extends Fragment implements View.OnClickListener {

    Button facebookLogin, googleLogin, otherLogin, offlineLogin;

    public ConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection, container, false);
        setViews(view);

       int MY_CAMERA_REQUEST_CODE = 100;
       int MY_STORAGE_REQUEST_CODE = 90;
       int MY_LOCATION_REQUEST_CODE = 80;
       int MY_MANAGER_REQUEST_CODE = 75;

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_STORAGE_REQUEST_CODE);
        } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
        return view;


    }

    private void setViews(View view){
        facebookLogin = view.findViewById(R.id.facebook_login);
        googleLogin = view.findViewById(R.id.google_login);
        otherLogin = view.findViewById(R.id.other_login);
        offlineLogin = view.findViewById(R.id.offline_login);
        facebookLogin.setOnClickListener(this);
        googleLogin.setOnClickListener(this);
        otherLogin.setOnClickListener(this);
        offlineLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent secondActivity = new Intent(getActivity(), SecondActivity.class);
        int id = view.getId();
        switch (id){
            case R.id.facebook_login:
                return;
            case R.id.google_login:
                return;
            case R.id.other_login:
                return;
            case R.id.offline_login:
                startActivity(secondActivity);
                return;
        }
    }
}

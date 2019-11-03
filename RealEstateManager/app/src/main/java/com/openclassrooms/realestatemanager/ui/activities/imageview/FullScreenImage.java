package com.openclassrooms.realestatemanager.ui.activities.imageview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;

import java.io.File;

public class FullScreenImage extends AppCompatActivity {
    private ImageView imageView;
    private RealEstateManagerAPIService service = DI.getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        imageView = findViewById(R.id.full_screen_image);
        Glide.with(this).load(service.getPhoto().getPhotoUrl()).into(imageView);
    }
}

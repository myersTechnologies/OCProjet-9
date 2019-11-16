package com.openclassrooms.realestatemanager.ui.activities.imageview;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;


public class FullScreenImage extends AppCompatActivity {
    private ImageView imageView;
    private RealEstateManagerAPIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        service = DI.getService();

        Photo photo = service.getPhoto();

        imageView = findViewById(R.id.full_image_view);

        Glide.with(imageView.getContext()).load(photo.getPhotoUrl()).apply(RequestOptions.noAnimation()).into(imageView);
    }
}

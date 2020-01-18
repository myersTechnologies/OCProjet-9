package com.openclassrooms.realestatemanager.ui.fragments.details;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.MediaFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment {

    private RecyclerView photosList;
    private MediaFragmentAdapter adapter;
    private static MediaFragment mediaFragment;
    private SaveToDatabase database = SaveToDatabase.getInstance(getContext());
    private House house;
    private  List<Photo> mediaPhotos;
    private InfoFragment infoFragment;

    public static MediaFragment newInstance(){
        mediaFragment = new MediaFragment();
        return mediaFragment;
    }

    public MediaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_media, container, false);
        setRetainInstance(false);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
       photosList = view.findViewById(R.id.photos_list);
        RealEstateManagerAPIService service = DI.getService();

        configureAndShowDetailsFragment();

        if (service.getHouse() != null) {
            if (house == null){
                house = service.getHouse();
            }
            mediaPhotos = new ArrayList<>();
            if (database.photoDao().getPhotos() != null) {
                for (int i = 0; i < database.photoDao().getPhotos().size(); i++) {
                    Photo photo = database.photoDao().getPhotos().get(i);
                    if (photo.getHouseId().equals(String.valueOf(house.getId()))) {
                        mediaPhotos.add(photo);
                    }
                }
            }
            GridLayoutManager gridLayoutManager;
            adapter = new MediaFragmentAdapter(mediaPhotos, getActivity());
            if (infoFragment == null) {
                gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
            } else {
                gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
            }
            photosList.setLayoutManager(gridLayoutManager);
            photosList.setAdapter(adapter);

        }
        return view;
    }

    public void updateAdapter(House house){
        if (house != null) {
            this.house = house;
            mediaPhotos = new ArrayList<>();
            if (database.photoDao().getPhotos() != null) {
                for (int i = 0; i < database.photoDao().getPhotos().size(); i++) {
                    Photo photo = database.photoDao().getPhotos().get(i);
                    if (photo.getHouseId().equals(String.valueOf(house.getId()))) {
                        mediaPhotos.add(photo);
                    }
                }
            }
            adapter = new MediaFragmentAdapter(mediaPhotos, getActivity());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
            photosList.setLayoutManager(gridLayoutManager);
            photosList.setAdapter(adapter);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
            photosList.setLayoutManager(gridLayoutManager);
            photosList.setAdapter(null);
        }
    }

    protected void configureAndShowDetailsFragment() {
        infoFragment = (InfoFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_info);
        if (infoFragment == null && getActivity().findViewById(R.id.fragment_container_details) != null){
            infoFragment = new InfoFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_details, infoFragment).commit();
        }
    }

}


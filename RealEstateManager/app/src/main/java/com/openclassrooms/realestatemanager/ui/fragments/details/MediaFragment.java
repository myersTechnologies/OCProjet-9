package com.openclassrooms.realestatemanager.ui.fragments.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.MediaFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment {

    private GridView photosList;
    private MediaFragmentAdapter adapter;
    private static MediaFragment mediaFragment;
    private SaveToDatabase database = SaveToDatabase.getInstance(getContext());

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
       photosList = view.findViewById(R.id.photos_list);
        RealEstateManagerAPIService service = DI.getService();
        List<Photo> mediaPhotos = new ArrayList<>();
        if (database.photoDao().getPhotos() != null) {
            for (int i = 0; i < database.photoDao().getPhotos().size(); i++) {
                Photo photo = database.photoDao().getPhotos().get(i);
                if (photo.getHouseId().equals(String.valueOf(service.getHouse().getId()))) {
                    mediaPhotos.add(photo);
                }
            }
        }
        adapter = new MediaFragmentAdapter(mediaPhotos, getActivity());
        photosList.setAdapter(adapter);
        return view;
    }

}


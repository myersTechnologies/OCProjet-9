package com.openclassrooms.realestatemanager.ui.fragments.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.events.ImageEvent;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.MediaFragmentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment {

    private GridView photosList;
    private MediaFragmentAdapter adapter;
    private static MediaFragment mediaFragment;
    private RealEstateManagerAPIService service = DI.getService();
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
        adapter = new MediaFragmentAdapter(service.getHouse().getImages(), getActivity());
        photosList.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getFullSizeImage(ImageEvent event) {
        service.setPhoto(event.photo);
    }
}


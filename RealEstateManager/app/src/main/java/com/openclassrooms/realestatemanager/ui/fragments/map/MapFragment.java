package com.openclassrooms.realestatemanager.ui.fragments.map;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.ui.activities.details.DetailsActivity;
import com.openclassrooms.realestatemanager.utils.places.GetNearbyPlacesData;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mapView;
    private LatLng current;
    private Location currentLocation;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        setRetainInstance(false);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();
            if (location != null) {
                currentLocation = location;
                current = new LatLng(location.getLatitude(), location.getLongitude());

                mapView.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_avatar_map)));
                mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

                //check all houses with addresses for map marker display
                for (int i = 0; i < SaveToDatabase.getInstance(DI.getService().getActivity()).adressDao().getAdresses().size(); i++){
                    AdressHouse adressHouse = SaveToDatabase.getInstance(DI.getService().getActivity()).adressDao().getAdresses().get(i);
                    LatLng housesPosition = getLocationFromAddress(getContext(), adressHouse.getAdress() + ", " + adressHouse.getCity());
                    for (int j = 0; j < SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().getHouses().size(); j++) {
                        House house = SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().getHouses().get(j);
                        if (house.getId().equals(adressHouse.getHouseId())) {
                            if (house.isAvailable()) {
                                mapView.addMarker(new MarkerOptions().position(housesPosition)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            } else {
                                mapView.addMarker(new MarkerOptions().position(housesPosition));
                            }
                        }
                    }
                }

                getMapMarker();

            }
        }
    };

    //get google maps points of interest
    public void getMapMarker(){
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
              current.latitude  + "," + current.longitude +
                "&radius=100&type=point_of_interst&key=AIzaSyBE7FhkDrMMk12zVVn_HR1IlcGZoKc3-oQ";
        Log.d("URLHERE",url);
        Object dataTransfer[] = new Object[3];
        dataTransfer[0] = url;
        dataTransfer[1] = getContext();
        dataTransfer[2] = mapView;
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapView = googleMap;
        mapView.setOnMarkerClickListener(this);
        mapView.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient mFusedLocationClient = null;

            LocationRequest mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            if (mFusedLocationClient == null) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
                mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                        locationCallback,
                        null /* Looper */);

            }
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle() != null){
            if (marker.isInfoWindowShown()){
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }
        }

        for (int i = 0; i < SaveToDatabase.getInstance(DI.getService().getActivity()).adressDao().getAdresses().size(); i++){
            AdressHouse adressHouse = SaveToDatabase.getInstance(DI.getService().getActivity()).adressDao().getAdresses().get(i);
            LatLng housesPosition = getLocationFromAddress(getContext(), adressHouse.getAdress() + ", " + adressHouse.getCity());
           for (int j = 0; j < SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().getHouses().size(); j++){
               House house = SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().getHouses().get(j);
               if (house.getId().equals(adressHouse.getHouseId()) && marker.getPosition().equals(housesPosition)){
                   DI.getService().setHouse(house);
                   Intent intent = new Intent(getContext(), DetailsActivity.class);
                   startActivity(intent);
               }
           }
        }
        return true;
    }
}

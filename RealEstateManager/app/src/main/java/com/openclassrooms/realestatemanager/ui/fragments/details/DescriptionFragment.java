package com.openclassrooms.realestatemanager.ui.fragments.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.DescriptionAdapter;



public class DescriptionFragment extends Fragment {

    private RecyclerView descriptionRv;
    private SaveToDatabase database = SaveToDatabase.getInstance(getContext());
    private RealEstateManagerAPIService service = DI.getService();

    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        descriptionRv = view.findViewById(R.id.description_rv);
        if (service.getHouse() != null){
            upDateDescription(service.getHouse());
        }
        return view;
    }

    public void upDateDescription(House house){
        HouseDetails details = database.houseDetailsDao().getDetailsWithHouseId(house.getId());
        DescriptionAdapter adapter = new DescriptionAdapter(details);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        descriptionRv.setLayoutManager(manager);
        descriptionRv.setAdapter(adapter);

    }

}

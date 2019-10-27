package com.openclassrooms.realestatemanager.ui.adapters.addnewhouse;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.InfoFragmentAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddNewHouseAdapter extends RecyclerView.Adapter<AddNewHouseAdapter.ViewHolder> {

    private RealEstateManagerAPIService service = DI.getService();

    public AddNewHouseAdapter(){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_new_house, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {


        private EditText surfaceText;
        private EditText roomsText;
        private EditText bathroomsText;
        private EditText bedroomsText;
        private EditText locationText;
        private EditText descriptionText;
        private EditText city;
        private EditText zipCode;
        private EditText state;
        private EditText country;
        private EditText price;
        private ViewHolder(View itemView) {
            super(itemView);

            surfaceText = itemView.findViewById(R.id.surface_edit_text);
            roomsText = itemView.findViewById(R.id.rooms_edit_text);
            bathroomsText = itemView.findViewById(R.id.bathrooms_edit_text);
            bedroomsText = itemView.findViewById(R.id.bedrooms_edit_text);
            locationText = itemView.findViewById(R.id.location_edit_text);
            city = itemView.findViewById(R.id.location_edit_city_text);
            zipCode = itemView.findViewById(R.id.location_edit_zc_text);
            state = itemView.findViewById(R.id.location_edit_state_text);
            country = itemView.findViewById(R.id.location_edit_country_text);
            descriptionText = itemView.findViewById(R.id.description_edit_text);
            price = itemView.findViewById(R.id.price_text);




        }
    }
}

package com.openclassrooms.realestatemanager.ui.adapters.modify;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;

public class ModifyAdapter extends RecyclerView.Adapter<ModifyAdapter.ViewHolder> {

    private House house;

    public ModifyAdapter(House house){
        this.house = house;
    }

    @Override
    public ModifyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_new_house, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.surfaceText.setText(String.valueOf(house.getSurface()));
        holder.roomsText.setText(String.valueOf(house.getRoomsNumber()));
        holder.bathroomsText.setText(String.valueOf(house.getBathroomsNumber()));
        holder.bedroomsText.setText(String.valueOf(house.getBedroomsNumber()));
        holder.locationText.setText(house.getAdress());
        holder.descriptionText.setText(house.getDescription());
        holder.city.setText(house.getCity());
        holder.zipCode.setText(house.getZipCode());
        holder.state.setText(house.getState());
        holder.country.setText(house.getCountry());
        holder.price.setText(String.valueOf(house.getPrice()));
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

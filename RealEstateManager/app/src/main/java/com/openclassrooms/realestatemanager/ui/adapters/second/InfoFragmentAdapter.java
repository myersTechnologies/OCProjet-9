package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;


public class InfoFragmentAdapter extends RecyclerView.Adapter<InfoFragmentAdapter.ViewHolder> {

    private House house;

    public InfoFragmentAdapter(House house) {
        this.house = house;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_details_layout, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.surfaceText.setText(String.valueOf(house.getSurface()) + " " + "sq m");
        holder.roomsText.setText(String.valueOf(house.getRoomsNumber()));
        holder.bathroomsText.setText(String.valueOf(house.getBathroomsNumber()));
        holder.bedroomsText.setText(String.valueOf(house.getBedroomsNumber()));
        holder.locationText.setText(house.getAdress() + "\n" + house.getCity() + "\n" + house.getState() +
                "\n" + house.getZipCode() + "\n" + house.getCountry());
        holder.descriptionText.setText(house.getDescription());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {

        private TextView surfaceText;
        private TextView roomsText;
        private TextView bathroomsText;
        private TextView bedroomsText;
        private TextView locationText;
        private TextView descriptionText;

        private ViewHolder(View itemView) {
            super(itemView);

            surfaceText = itemView.findViewById(R.id.surface_text);
            roomsText = itemView.findViewById(R.id.rooms_text);
            bathroomsText = itemView.findViewById(R.id.bathrooms_text);
            bedroomsText = itemView.findViewById(R.id.bedrooms_text);
            locationText = itemView.findViewById(R.id.location_text);
            descriptionText = itemView.findViewById(R.id.description_text);

        }
    }
}

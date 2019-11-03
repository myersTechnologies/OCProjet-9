package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;


public class InfoFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private House house;
    private RealEstateManagerAPIService service = DI.getService();

    public InfoFragmentAdapter(House house) {
        this.house = house;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_details_layout,parent,false);
            viewHolder = new ViewHolder(view);
        }

        if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_details_second,parent,false);
            viewHolder = new SecondViewHolder(view);
        }

        if (viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_layout, parent,false);
            viewHolder = new MapViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderView, int position) {
        if (holderView.getItemViewType() == 0) {
            ViewHolder holder = (ViewHolder) holderView;
            holder.surfaceText.setText(String.valueOf(house.getSurface()) + " " + "sq m");
            holder.roomsText.setText(String.valueOf(house.getRoomsNumber()));
            holder.bathroomsText.setText(String.valueOf(house.getBathroomsNumber()));
            holder.bedroomsText.setText(String.valueOf(house.getBedroomsNumber()));
            holder.locationText.setText(house.getAdress() + "\n" + house.getCity() + "\n" + house.getState() +
                    "\n" + house.getZipCode() + "\n" + house.getCountry());
            holder.descriptionText.setText(house.getDescription());
        }

        if (holderView.getItemViewType() == 1) {
            SecondViewHolder holder = (SecondViewHolder) holderView;
            holder.priceTitle.setText(house.getPrice());

            for (int i = 0; i < service.getUsers().size(); i++){
                if (service.getUsers().get(i).getUserId().equals(house.getAgentId())){
                    Glide.with(holder.itemView.getContext()).load(service.getUsers().get(i).getPhotoUri())
                            .apply(RequestOptions.circleCropTransform()).into(holder.agentAvatar);
                    holder.agentName.setText(service.getUsers().get(i).getName());
                }
            }

            if (house.isAvailable()) {
                holder.isAvailableImg.setImageResource(R.drawable.for_sale);
            } else {
                holder.isAvailableImg.setImageResource(R.drawable.sold);
            }

        }

        if (holderView.getItemViewType() == 2) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        int i = 0;
        switch (position){
            case 0:
                i = 0;
                return 0;
            case 1:
                i = 1;
                return 1;
            case 2 :
                i = 2;
                return 2;
        }
        return i;
    }

    @Override
    public int getItemCount() {
        return 3;
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

    static class SecondViewHolder extends RecyclerView.ViewHolder{

        private TextView priceTitle;
        private ImageView agentAvatar;
        private TextView agentName;
        private ImageView isAvailableImg;

        public SecondViewHolder(View itemView) {
            super(itemView);
            priceTitle = itemView.findViewById(R.id.details_price_title);
            agentAvatar = itemView.findViewById(R.id.agent_details_avatar);
            agentName = itemView.findViewById(R.id.agent_details_name);
            isAvailableImg = itemView.findViewById(R.id.is_available_img);
        }
    }

    static class MapViewHolder extends RecyclerView.ViewHolder {

        public MapViewHolder(View itemView) {
            super(itemView);

        }

    }
}

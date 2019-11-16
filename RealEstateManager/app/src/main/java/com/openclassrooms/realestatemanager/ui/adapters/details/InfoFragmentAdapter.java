package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.text.DecimalFormat;


public class InfoFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private House house;
    private RealEstateManagerAPIService service;
    private HouseDetails details;
    private SaveToDatabase database;
    private Context context;

    public InfoFragmentAdapter(House house, HouseDetails details, Context context) {
        this.house = house;
        service = DI.getService();
        this.details = details;
        this.context = context;
        database = SaveToDatabase.getInstance(context);
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


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderView, int position) {
        if (holderView.getItemViewType() == 0) {
            ViewHolder holder = (ViewHolder) holderView;
            if (details != null) {

                holder.surfaceText.setText(Utils.getMeasureWithMeasureSystem(house, details));

                holder.roomsText.setText(String.valueOf(details.getRoomsNumber()));
                holder.bathroomsText.setText(String.valueOf(details.getBathroomsNumber()));
                holder.bedroomsText.setText(String.valueOf(details.getBedroomsNumber()));
            for (int i = 0; i < database.adressDao().getAdresses().size(); i++){
                AdressHouse adressHouse = database.adressDao().getAdresses().get(i);
                if (adressHouse.getHouseId().equals(String.valueOf(house.getId()))){
                    AdressHouse findedHouse = adressHouse;
                    holder.locationText.setText(findedHouse.getAdress() + "\n" + findedHouse.getCity() + "\n" + findedHouse.getState() +
                            "\n" + findedHouse.getZipCode() + "\n" + findedHouse.getCountry());
                }
            }
            holder.descriptionText.setText(details.getDescription());
        }
        }

        if (holderView.getItemViewType() == 1) {
            SecondViewHolder holder = (SecondViewHolder) holderView;

            String valeurString = house.getPrice();
            String valeurBrute = valeurString.replaceAll(",", "");
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            holder.priceTitle.setText(Utils.getPriceWithMonetarySystem(valeurBrute, house, formatter));

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

    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return 0;
            case 1:
                return 1;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        return 2;
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

}

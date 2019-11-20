package com.openclassrooms.realestatemanager.ui.adapters.modify;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.utils.AddModifyHouseHelper;

import java.util.Arrays;
import java.util.List;

public class ModifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static House house;

    private int LAYOUT_ONE = 0;
    private int LAYOUT_TWO = 1;
    private int LAYOUT_TREE = 2;
    private int LAYOUT_FOUR = 3;
    private int LAYOUT_FIVE = 4;
    private int LAYOUT_SIX = 5;
    private int COUNT = 6;

    private AdressHouse adressHouse;

    private List<Photo> photos;
    private HouseDetails details;

    public ModifyAdapter(House house, List<Photo> photos, HouseDetails details, Context context) {
        adressHouse = SaveToDatabase.getInstance(context).adressDao().getAdressWithHouseId(house.getId());
        if (AddModifyHouseHelper.getHouse() == null) {
            AddModifyHouseHelper.ModifyHouse(house, adressHouse, details, photos);
            this.house = house;
            this.photos = photos;
            this.details = details;
        } else {
            this.house = AddModifyHouseHelper.getHouse();
            this.photos = AddModifyHouseHelper.getPhotos();
            this.details = AddModifyHouseHelper.getHouseDetails();
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType== LAYOUT_ONE)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_modify_house,parent,false);
            viewHolder = new ViewHolder(view);
        }
       if (viewType == LAYOUT_TWO)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_insert_data,parent,false);
            viewHolder= new LocationViewHolder(view);
        }

       if (viewType == LAYOUT_TREE){
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.description_edit_layout,parent,false);
           viewHolder= new DescriptionViewHolder(view);
       }

       if (viewType == LAYOUT_FOUR){
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_header_expendable_list,parent,false);
           viewHolder = new TitleViewHolder(view);
       }

        if (viewType == LAYOUT_FIVE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_layout, parent, false);
            viewHolder = new ImageViewViewHolder(view);

        }

        if (viewType == LAYOUT_SIX){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sell_status_layout, parent, false);
            viewHolder = new StatusViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        switch (position){
            case 0:
                return LAYOUT_ONE;
            case 1:
                return LAYOUT_TWO;
            case 2:
                return LAYOUT_TREE;
            case 3:
                return LAYOUT_FOUR;
            case 4:
                return LAYOUT_FIVE;
            case 5:
                return LAYOUT_SIX;
        }
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderView, int position) {

        if (holderView.getItemViewType() == LAYOUT_ONE) {
            ViewHolder holder = (ViewHolder) holderView;
            setTextToViewHolder(holder);
            setTextWatchersViewHolder(holder);
        } else if (holderView.getItemViewType() == LAYOUT_TWO){
            LocationViewHolder locationViewHolder = (LocationViewHolder) holderView;
            setLocationTextToViewHolder(locationViewHolder);
            setLocationTextWatcher(locationViewHolder);
        } else if (holderView.getItemViewType() == LAYOUT_TREE){
            DescriptionViewHolder descriptionHolder = (DescriptionViewHolder) holderView;
            setDescriptionTextToViewHolder(descriptionHolder);
            setTextWatcherToDescriptionViewHolder(descriptionHolder);
        } else if (holderView.getItemViewType() == LAYOUT_FIVE){
             ImageViewViewHolder imageViewViewHolder = (ImageViewViewHolder) holderView;
             LinearLayoutManager layoutManager = new LinearLayoutManager(imageViewViewHolder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
             imageViewViewHolder.imageRecyclerView.setLayoutManager(layoutManager);
             PhotoListAdapter adapter;
             adapter = new PhotoListAdapter(AddModifyHouseHelper.getPhotos(), house.getId(), imageViewViewHolder.itemView.getContext());
             imageViewViewHolder.imageRecyclerView.setAdapter(adapter);
        } if (holderView.getItemViewType() == LAYOUT_SIX){
            StatusViewHolder statusViewHolder = (StatusViewHolder) holderView;
            setAvailibilityListener(statusViewHolder);
        }
    }

    private void setTextToViewHolder(ViewHolder holder){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, AddModifyHouseHelper.getHousesTypes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.nameText.setAdapter(adapter);
        holder.nameText.setSelection(Arrays.asList(AddModifyHouseHelper.getHousesTypes()).indexOf(house.getName()));
        holder.surfaceText.setText(String.valueOf(details.getSurface()));
        holder.roomsText.setText(String.valueOf(details.getRoomsNumber()));
        holder.bathroomsText.setText(String.valueOf(details.getBathroomsNumber()));
        holder.bedroomsText.setText(String.valueOf(details.getBedroomsNumber()));
        holder.price.setText(house.getPrice());
    }

    private void setLocationTextToViewHolder(LocationViewHolder locationViewHolder){
        locationViewHolder.locationText.setText(adressHouse.getAdress());
        locationViewHolder.city.setText(adressHouse.getCity());
        locationViewHolder.zipCode.setText(adressHouse.getZipCode());
        locationViewHolder.state.setText(adressHouse.getState());
        locationViewHolder.country.setText(adressHouse.getCountry());
    }

    private void setDescriptionTextToViewHolder(DescriptionViewHolder descriptionHolder){
        descriptionHolder.descriptionContent.setText(details.getDescription());
    }

    private void setTextWatchersViewHolder(final ViewHolder holder){

        AddModifyHouseHelper.spinnerListener(holder.nameText, holder.nameText.getContext());
        AddModifyHouseHelper.getEditTextTextWatcher(holder.surfaceText);
        AddModifyHouseHelper.getEditTextTextWatcher(holder.roomsText);
        AddModifyHouseHelper.getEditTextTextWatcher(holder.bedroomsText);
        AddModifyHouseHelper.getEditTextTextWatcher(holder.bathroomsText);
        AddModifyHouseHelper.getEditTextTextWatcher(holder.price);

    }

    private void setLocationTextWatcher(LocationViewHolder locationViewHolder){
        AddModifyHouseHelper.getEditTextTextWatcher(locationViewHolder.locationText);
        AddModifyHouseHelper.getEditTextTextWatcher(locationViewHolder.city);
        AddModifyHouseHelper.getEditTextTextWatcher(locationViewHolder.state);
        AddModifyHouseHelper.getEditTextTextWatcher(locationViewHolder.zipCode);
        AddModifyHouseHelper.getEditTextTextWatcher(locationViewHolder.country);
    }

    private void setTextWatcherToDescriptionViewHolder(DescriptionViewHolder descriptionHolder){
        AddModifyHouseHelper.getEditTextTextWatcher(descriptionHolder.descriptionContent);
    }
    private void setAvailibilityListener(StatusViewHolder statusViewHolder){
        AddModifyHouseHelper.setModifyCheckedListener(statusViewHolder.available, statusViewHolder.sold);
    }


    @Override
    public int getItemCount() {
        return COUNT;
    }


    static class ViewHolder extends RecyclerView.ViewHolder  {

        private Spinner nameText;
        private EditText surfaceText;
        private EditText roomsText;
        private EditText bathroomsText;
        private EditText bedroomsText;
        private EditText price;
        private ViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name_edit_text_edit);
            surfaceText = itemView.findViewById(R.id.surface_edit_text_edit);
            roomsText = itemView.findViewById(R.id.rooms_edit_text_edit);
            bathroomsText = itemView.findViewById(R.id.bathrooms_edit_text_edit);
            bedroomsText = itemView.findViewById(R.id.bedrooms_edit_text_edit);
            price = itemView.findViewById(R.id.price_text_edit);

            surfaceText.setTag("Surface");
            roomsText.setTag("Rooms");
            bathroomsText.setTag("Bathrooms");
            bedroomsText.setTag("Bedrooms");
            price.setTag("Price");

            AddModifyHouseHelper.addToData(surfaceText);
            AddModifyHouseHelper.addToData(roomsText);
            AddModifyHouseHelper.addToData(bathroomsText);
            AddModifyHouseHelper.addToData(bedroomsText);
            AddModifyHouseHelper.addToData(price);

        }
    }

    static class DescriptionViewHolder extends RecyclerView.ViewHolder{

        private EditText descriptionContent;

        public DescriptionViewHolder(View itemView) {
            super(itemView);

            descriptionContent = itemView.findViewById(R.id.description_edit_text_edit);
            descriptionContent.setTag("Description");
            AddModifyHouseHelper.addToData(descriptionContent);

        }
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder{

        private EditText locationText;
        private EditText city;
        private EditText zipCode;
        private EditText state;
        private EditText country;

        public LocationViewHolder(View itemView) {
            super(itemView);

            locationText = itemView.findViewById(R.id.location_edit_text_edit);
            city = itemView.findViewById(R.id.location_edit_city_text_edit);
            zipCode = itemView.findViewById(R.id.location_edit_zc_text_edit);
            state = itemView.findViewById(R.id.location_edit_state_text_edit);
            country = itemView.findViewById(R.id.location_edit_country_text_edit);

            locationText.setTag("Road and Adress");
            city.setTag("City");
            zipCode.setTag("ZipCode");
            state.setTag("State");
            country.setTag("Country");

            AddModifyHouseHelper.addToData(locationText);
            AddModifyHouseHelper.addToData(city);
            AddModifyHouseHelper.addToData(zipCode);
            AddModifyHouseHelper.addToData(state);
            AddModifyHouseHelper.addToData(country);
        }
    }
    static class ImageViewViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView imageRecyclerView;

        public ImageViewViewHolder(View itemView) {
            super(itemView);

            imageRecyclerView = itemView.findViewById(R.id.photos_items_list);

        }
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder{
        public TitleViewHolder(View itemView) {
            super(itemView);
        }
    }
    static class StatusViewHolder extends RecyclerView.ViewHolder{
        private CheckedTextView available;
        private CheckedTextView sold;

        public StatusViewHolder(View itemView) {
            super(itemView);

            available = itemView.findViewById(R.id.to_sell_checktxt);
            sold = itemView.findViewById(R.id.sold_checkedtxt);
        }
    }

}

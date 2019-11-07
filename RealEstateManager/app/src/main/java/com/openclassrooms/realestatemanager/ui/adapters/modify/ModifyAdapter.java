package com.openclassrooms.realestatemanager.ui.adapters.modify;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.ui.adapters.addnewhouse.AddNewHouseAdapter;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static House house;
    private String[] houseTypes = new String[]{"Select...", "Maison", "Appartement", "Terrain", "Propriété", "Commerce", "Bureau",
    "Immeuble", "Parking/Garage", "Château", "Manoir"};

    private int LAYOUT_ONE = 0;
    private int LAYOUT_TWO = 1;
    private int LAYOUT_TREE = 2;
    private int LAYOUT_FOUR = 3;
    private int LAYOUT_FIVE = 4;
    private int LAYOUT_SIX = 5;

    private static AdressHouse adressHouse;

    private static List<EditText> data;

    private static List<Photo> photos;
    private static HouseDetails details;

    public ModifyAdapter(House house, List<Photo> photos, HouseDetails details){
        this.house = house;
        data = new ArrayList<>();
        this.photos = photos;
        this.details = details;
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
           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.description_layout,parent,false);
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
        int layout = 0;

        switch (position){
            case 0:
                layout = LAYOUT_ONE;
                return layout;
            case 1:
                layout = LAYOUT_TWO;
                return layout;
            case 2:
                layout = LAYOUT_TREE;
                return layout;
            case 3:
                layout = LAYOUT_FOUR;
                return layout;
            case 4:
                layout = LAYOUT_FIVE;
                return layout;
            case 5:
                layout = LAYOUT_SIX;
                return layout;
        }
        return layout;
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
             adapter = new PhotoListAdapter(photos, house.getId());
             imageViewViewHolder.imageRecyclerView.setAdapter(adapter);
        } if (holderView.getItemViewType() == LAYOUT_SIX){
            final StatusViewHolder statusViewHolder = (StatusViewHolder) holderView;
            if (house.isAvailable()){
                statusViewHolder.available.setChecked(true);
                statusViewHolder.available.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
            } else {
                statusViewHolder.sold.setChecked(true);
                statusViewHolder.available.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
            }
            statusViewHolder.available.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (statusViewHolder.available.isChecked()){
                        statusViewHolder.available.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                        statusViewHolder.available.setChecked(false);
                    } else {
                        statusViewHolder.available.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
                        statusViewHolder.available.setChecked(true);
                        if (statusViewHolder.sold.isChecked()) {
                            statusViewHolder.sold.setChecked(false);
                            statusViewHolder.sold.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                        }
                        house.setAvailable(true);
                    }
                }
            });
            statusViewHolder.sold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (statusViewHolder.sold.isChecked()){
                        statusViewHolder.sold.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                        statusViewHolder.sold.setChecked(false);
                        if (details.getSoldDate() != null){
                            details.setSoldDate(null);
                        }
                    } else {
                        statusViewHolder.sold.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
                        statusViewHolder.sold.setChecked(true);
                        if (statusViewHolder.available.isChecked()){
                            statusViewHolder.available.setChecked(false);
                            statusViewHolder.available.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                        }
                        house.setAvailable(false);
                        details.setSoldDate(Utils.getTodayDate());
                    }
                }
            });
        }
    }

    private void setTextToViewHolder(ViewHolder holder){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, houseTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.nameText.setAdapter(adapter);
        holder.nameText.setSelection(Arrays.asList(houseTypes).indexOf(house.getName()));
        holder.surfaceText.setText(String.valueOf(details.getSurface()));
        holder.roomsText.setText(String.valueOf(details.getRoomsNumber()));
        holder.bathroomsText.setText(String.valueOf(details.getBathroomsNumber()));
        holder.bedroomsText.setText(String.valueOf(details.getBedroomsNumber()));
        holder.price.setText(house.getPrice());
    }

    private void setLocationTextToViewHolder(LocationViewHolder locationViewHolder){
        List<AdressHouse> adresses = DI.getService().getAdressesList();
        for (int i = 0; i < adresses.size(); i++){
            if (adresses.get(i).getHouseId().equals(String.valueOf(house.getId()))){
                adressHouse = adresses.get(i);
            }
        }
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

        holder.nameText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                house.setName(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.surfaceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    details.setSurface(editable.toString());
                }
            }
        });

        holder.bathroomsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    details.setBathroomsNumber(editable.toString());
                }
            }
        });

        holder.roomsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    details.setRoomsNumber(editable.toString());
                }
            }
        });
        holder.bedroomsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    details.setBedroomsNumber(editable.toString());
                }
            }
        });

        holder.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    DecimalFormat formatter = new DecimalFormat("###,###,###");
                    if (editable.toString().contains(",")) {
                        house.setPrice(formatter.format(Integer.parseInt(editable.toString().replaceAll(",", ""))).replaceAll("\\s", ","));
                    } else {
                        house.setPrice(formatter.format(Integer.parseInt(editable.toString())).replaceAll("\\s", ","));
                    }
                }
            }
        });

    }

    private void setLocationTextWatcher(LocationViewHolder locationViewHolder){
        locationViewHolder.locationText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
               adressHouse.setAdress(editable.toString());
            }
        });
        locationViewHolder.city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                adressHouse.setCity(editable.toString());
            }
        });
        locationViewHolder.zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    adressHouse.setZipCode(editable.toString());
                }
            }
        });
        locationViewHolder.state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adressHouse.setState(editable.toString());
            }
        });
        locationViewHolder.country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                adressHouse.setCountry(editable.toString());
            }
        });
    }

    private void setTextWatcherToDescriptionViewHolder(DescriptionViewHolder descriptionHolder){
        descriptionHolder.descriptionContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
               details.setDescription(editable.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
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

            data.add(surfaceText);
            data.add(roomsText);
            data.add(bathroomsText);
            data.add(bedroomsText);
            data.add(price);

        }
    }

    static class DescriptionViewHolder extends RecyclerView.ViewHolder{

        private EditText descriptionContent;

        public DescriptionViewHolder(View itemView) {
            super(itemView);

            descriptionContent = itemView.findViewById(R.id.description_edit_text_edit);
            descriptionContent.setTag("Description");
            data.add(descriptionContent);

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

            data.add(locationText);
            data.add(city);
            data.add(zipCode);
            data.add(state);
            data.add(country);
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


    public static List<EditText> getData(){
        return data;
    }

    public static House gethouse(){
        return house;
    }


    public static List<Photo> getPhotos(){
        return photos;
    }

    public static HouseDetails getDetails(){return details;}

    public static AdressHouse getAdressHouse(){return adressHouse;}

}

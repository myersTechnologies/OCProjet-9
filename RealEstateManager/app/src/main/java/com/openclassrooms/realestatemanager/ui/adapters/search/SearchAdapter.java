package com.openclassrooms.realestatemanager.ui.adapters.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Search;
import com.openclassrooms.realestatemanager.ui.activities.second.SecondActivity;
import com.openclassrooms.realestatemanager.ui.adapters.details.InfoFragmentAdapter;
import com.openclassrooms.realestatemanager.ui.adapters.details.PointsAdapter;
import com.openclassrooms.realestatemanager.utils.AddModifyHouseHelper;
import com.openclassrooms.realestatemanager.utils.SearchHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private SaveToDatabase database;

    public SearchAdapter(Context context) {
        this.context = context;
        database = SaveToDatabase.getInstance(context);
        SearchHelper.setNewSearch();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_layout,parent,false);
            viewHolder = new SpinnerViewHolder(view);
        }

        if(viewType== 1 ||viewType== 2|| viewType == 3|| viewType == 4||viewType ==5) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_layout_choice,parent,false);
            viewHolder = new SearchViewHolder(view);

        }
        if (viewType == 6){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_layout,parent,false);
            viewHolder = new SpinnerViewHolder(view);
        }

        if (viewType == 7){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.money_type_layout,parent,false);
            viewHolder = new AvailableViewHolder(view);
        }

        if (viewType == 8){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.points_layout,parent,false);
            viewHolder = new PointsViewHolder(view);
        }


        if (viewType == 9){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_layout,parent,false);
            viewHolder = new ButtonViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderView, int position) {
        if (position == 0){
            SpinnerViewHolder holder = (SpinnerViewHolder) holderView;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, AddModifyHouseHelper.getHousesTypes());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setTag("Name");
            holder.spinner.setAdapter(adapter);
            getSpinnerListener(holder);
        }
        if (position == 1){
            SearchViewHolder holder = (SearchViewHolder) holderView;
            holder.title.setText("Surface");
            holder.minText.setTag("Surface_Min");
            holder.maxText.setTag("Surface_Max");
            getEditTextText(holder);
        }
        if (position == 2){
            SearchViewHolder holder = (SearchViewHolder) holderView;
            holder.title.setText("Rooms");
            holder.minText.setTag("Rooms_Min");
            holder.maxText.setTag("Rooms_Max");
            getEditTextText(holder);
        }
        if (position == 3){
            SearchViewHolder holder = (SearchViewHolder) holderView;
            holder.title.setText("Bathrooms");
            holder.minText.setTag("Bathrooms_Min");
            holder.maxText.setTag("Bathrooms_Max");
            getEditTextText(holder);
        }
        if (position == 4){
            SearchViewHolder holder = (SearchViewHolder) holderView;
            holder.title.setText("Bedrooms");
            holder.minText.setTag("Bedrooms_Min");
            holder.maxText.setTag("Bedrooms_Max");
            getEditTextText(holder);
        }
        if (position == 5){
            SearchViewHolder holder = (SearchViewHolder) holderView;
            holder.title.setText("Price");
            holder.minText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            holder.maxText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            holder.minText.setTag("Price_Min");
            holder.maxText.setTag("Price_Max");
            getEditTextText(holder);
        }

        if (position == 6){
            SpinnerViewHolder holder = (SpinnerViewHolder) holderView;
            List<String> cities = new ArrayList<>();
            cities.add("Choose a city...");
            for (int i = 0; i < database.adressDao().getAdresses().size(); i++){
                AdressHouse adressHouse = database.adressDao().getAdresses().get(i);
                if (!cities.contains(adressHouse.getCity())){
                    cities.add(adressHouse.getCity());
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, cities);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setTag("City");
            holder.spinner.setAdapter(adapter);
           getSpinnerCityListener(holder);
        }

        if (position == 7){
            AvailableViewHolder holder = (AvailableViewHolder) holderView;
            holder.titleText.setText("Availability");
            holder.choice1Text.setText("On sale");
            holder.choice2Text.setText("Sold");
            SearchHelper.getSearch().setAvailability("none");
            getSwitchListener(holder);
        }

        if (position == 8){
           PointsViewHolder pointsViewHolder = (PointsViewHolder)holderView;
            SearchPointsAdapter adapter = new SearchPointsAdapter();
            LinearLayoutManager manager = new LinearLayoutManager(pointsViewHolder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            pointsViewHolder.pointsRv.setLayoutManager(manager);
            pointsViewHolder.pointsRv.setAdapter(adapter);
        }

        if (position == 9){
           ButtonViewHolder holder = (ButtonViewHolder)holderView;
           getConfirmButtonSearch(holder);
        }
    }

    private void getSpinnerCityListener(SpinnerViewHolder holder) {
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() != "Choose a city...") {
                    SearchHelper.getSearch().setCity(adapterView.getItemAtPosition(i).toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getSpinnerListener(SpinnerViewHolder holder) {
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString() != "Select Type...") {
                    SearchHelper.getSearch().setName(adapterView.getItemAtPosition(i).toString());
                    Toast.makeText(DI.getService().getActivity(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getSwitchListener(AvailableViewHolder holder) {
        holder.availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    SearchHelper.getSearch().setAvailability("false");
                } else {
                    SearchHelper.getSearch().setAvailability("true");
                }
            }
        });
    }

    private void getConfirmButtonSearch(ButtonViewHolder holder) {
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SecondActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void getEditTextText(final SearchViewHolder holder) {

        holder.minText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                 String tag = holder.minText.getTag().toString();
                if (!TextUtils.isEmpty(editable)) {
                    switch (tag) {
                        case "Surface_Min":
                            SearchHelper.getSearch().setSurfaceMin(editable.toString());
                            return;
                        case "Rooms_Min":
                            SearchHelper.getSearch().setRoomsMin(editable.toString());
                            return;
                        case "Bathrooms_Min":
                            SearchHelper.getSearch().setBathroomsMin(editable.toString());
                            return;
                        case "Bedrooms_Min":
                            SearchHelper.getSearch().setBedroomsMin(editable.toString());
                            return;
                        case "Price_Min":
                            SearchHelper.getSearch().setPriceMin(editable.toString());
                            return;
                    }
                }
            }
        });
        holder.maxText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String tag = holder.maxText.getTag().toString();
                if (!TextUtils.isEmpty(editable)) {
                    switch (tag) {
                        case "Surface_Max":
                            SearchHelper.getSearch().setSurfaceMax(editable.toString());
                            return;
                        case "Rooms_Max":
                            SearchHelper.getSearch().setRoomsMax(editable.toString());
                            return;
                        case "Bathrooms_Max":
                            SearchHelper.getSearch().setBathroomsMax(editable.toString());
                            return;
                        case "Bedrooms_Max":
                            SearchHelper.getSearch().setBedroomsMax(editable.toString());
                            return;
                        case "Price_Max":
                            SearchHelper.getSearch().setPriceMax(editable.toString());
                            return;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return 0;
            case  1:
                return 1;
            case 2:
                return  2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7 :
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
        }

        return position;
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private EditText minText;
        private EditText maxText;

        public SearchViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_search);
            minText = itemView.findViewById(R.id.minimum_edit_text);
            maxText = itemView.findViewById(R.id.maximum_edit_text);

            minText.setInputType(InputType.TYPE_CLASS_NUMBER);
            maxText.setInputType(InputType.TYPE_CLASS_NUMBER);

        }
    }

    static class AvailableViewHolder extends RecyclerView.ViewHolder{

        public SwitchCompat availableSwitch;
        public TextView titleText;
        public TextView choice1Text;
        public TextView choice2Text;


        public AvailableViewHolder(View itemView) {
            super(itemView);

            availableSwitch = itemView.findViewById(R.id.switch_system);
            titleText = itemView.findViewById(R.id.money_text);
            choice1Text = itemView.findViewById(R.id.dollar);
            choice2Text = itemView.findViewById(R.id.euro);
            choice1Text.setTextSize(20);
            choice2Text.setTextSize(20);

        }

    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder{

        private Button button;

        public ButtonViewHolder(View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.delete_account_btn);
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setText("Search");
            button.setTextColor(Color.BLACK);
        }
    }

    static class SpinnerViewHolder extends RecyclerView.ViewHolder{
        private Spinner spinner;
        public SpinnerViewHolder(View itemView) {
            super(itemView);
            spinner = itemView.findViewById(R.id.spinner_house);
        }
    }

    static class PointsViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView pointsRv;
        public PointsViewHolder(@NonNull View itemView) {
            super(itemView);

            pointsRv = itemView.findViewById(R.id.points_rv);
        }
    }

}

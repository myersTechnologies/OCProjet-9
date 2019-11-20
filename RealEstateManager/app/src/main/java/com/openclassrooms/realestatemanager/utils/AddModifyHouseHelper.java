package com.openclassrooms.realestatemanager.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AddModifyHouseHelper {

    private static List<EditText> data;
    private static House house;
    private static List<Photo> photos;
    private static AdressHouse adressHouse;
    private static HouseDetails houseDetails;
    private static RealEstateManagerAPIService service = DI.getService();

    public static void setNewAdd(SaveToDatabase database){
        data = new ArrayList<>();
        photos = new ArrayList<>();

        house = new House(UUID.randomUUID().toString(), "", "0", false,
                    service.getPreferences().getMonetarySystem(), service.getPreferences().getMeasureUnity());

        adressHouse = new AdressHouse("", "", "", "", "", "", "");
        houseDetails = new HouseDetails(house.getId(), house.getId());

    }

    public static void ModifyHouse(House house1, AdressHouse adress1, HouseDetails details1, List<Photo> photos1){
        house = house1;
        adressHouse = adress1;
        houseDetails = details1;
        photos = photos1;
        data = new ArrayList<>();
    }

    public static void spinnerListener(Spinner spinner, Context context){

        String[] houseTypes = AddModifyHouseHelper.getHousesTypes();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, houseTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (house.getName() != null) {
            spinner.setSelection(Arrays.asList(houseTypes).indexOf(house.getName()));
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                house.setName(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public static void getEditTextTextWatcher(final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tag = editText.getTag().toString();
                switch (tag){
                    case "Surface":
                        if (!TextUtils.isEmpty(editable)) {
                            houseDetails.setSurface(editable.toString());
                        }
                        break;
                    case "Rooms":
                        if (!TextUtils.isEmpty(editable)) {
                            houseDetails.setRoomsNumber(editable.toString());
                        }
                        break;
                    case "Bathrooms":
                        if (!TextUtils.isEmpty(editable)) {
                            houseDetails.setBathroomsNumber(editable.toString());
                        }
                        break;
                    case "Bedrooms":
                        if (!TextUtils.isEmpty(editable)) {
                            houseDetails.setBedroomsNumber(editable.toString());
                        }
                        break;
                    case "Price":
                        if (!TextUtils.isEmpty(editable)) {
                            DecimalFormat formatter = new DecimalFormat("###,###,###");
                            if (editable.toString().contains(",")) {
                                house.setPrice(formatter.format(Integer.parseInt(editable.toString().replaceAll(",", ""))).replaceAll("\\s", ","));
                            } else {
                                try {
                                    house.setPrice(formatter.format(Integer.parseInt(editable.toString())).replaceAll("\\s", ","));
                                } catch (NumberFormatException e){
                                    editable.clear();
                                }
                            }
                        }
                        break;
                    case "Road and Adress":
                        adressHouse.setAdress(editable.toString());
                        break;
                        case "City" :
                            adressHouse.setCity(editable.toString());
                            break;
                    case "ZipCode":
                        adressHouse.setZipCode(editable.toString());
                        break;
                    case "State":
                        adressHouse.setState(editable.toString());
                        break;
                    case "Country":
                        adressHouse.setCountry(editable.toString());
                        break;
                       case "Description":
                           houseDetails.setDescription(editable.toString());
                    break;
                }
            }
        });
    }

    public static void setCheckTextListener(final CheckedTextView available, CheckedTextView sold){
       available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (available.isChecked()){
                    available.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                    available.setChecked(false);
                } else {
                    available.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
                    available.setChecked(true);
                    house.setAvailable(true);
                    getCalendar("onSale", available);
                }
            }
        });
        sold.setEnabled(false);
    }

    private static void getCalendar(final String state, CheckedTextView checkedTextView){
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(checkedTextView.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                String date = mDay + "/" + (mMonth + 1) + "/" + mYear;
                switch (state){
                    case "onSale":
                        houseDetails.setOnLineDate(date);
                        house.setAvailable(true);
                        break;
                    case "sold":
                        house.setAvailable(false);
                        houseDetails.setSoldDate(date);

                        break;
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public static void setModifyCheckedListener(final CheckedTextView available, final CheckedTextView sold){
        //to modify house set state of house
        if (house.isAvailable()){
            available.setChecked(true);
            available.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
            sold.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);

        } else {
            sold.setChecked(true);
            available.setChecked(false);
            available.setEnabled(false);
            available.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
            sold.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
        }
        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (available.isChecked()){
                    available.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                    available.setChecked(false);
                } else {
                    available.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
                    available.setChecked(true);
                    getCalendar("onSale", available);
                    if (sold.isChecked()) {
                        sold.setChecked(false);
                        sold.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                    }
                }
            }
        });
        sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sold.isChecked()){
                    sold.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                    sold.setChecked(false);
                    if (houseDetails.getSoldDate() != null){
                        houseDetails.setSoldDate(null);
                    }
                } else {
                    sold.setCheckMarkDrawable(R.drawable.ic_check_green_24dp);
                    sold.setChecked(true);
                    getCalendar("sold", sold);
                    if (available.isChecked()){
                        available.setChecked(false);
                        available.setCheckMarkDrawable(R.drawable.ic_check_white_24dp);
                        getCalendar("onSale", available);
                    }

                }
            }
        });
    }

    public static String[] getHousesTypes(){
        String[] houseTypes = new String[]{"Select...", "Maison", "Appartement", "Terrain", "Propriété", "Commerce", "Bureau",
                "Immeuble", "Parking/Garage", "Château", "Manoir"};
        return houseTypes;
    }

    public static House getHouse(){
        return house;
    }

    public static void addToData(EditText editText){
        if (data != null){
            data.add(editText);
        }
    }

    public static List<Photo> getPhotos() {
        return photos;
    }

    public static AdressHouse getAdressHouse() {
        return adressHouse;
    }

    public static HouseDetails getHouseDetails() {
        return houseDetails;
    }

    public static List<EditText> getData() {
        return data;
    }
}

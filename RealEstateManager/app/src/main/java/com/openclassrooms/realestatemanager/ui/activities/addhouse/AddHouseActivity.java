package com.openclassrooms.realestatemanager.ui.activities.addhouse;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.addnewhouse.AddNewHouseAdapter;
import com.openclassrooms.realestatemanager.ui.adapters.modify.PhotoListAdapter;
import com.openclassrooms.realestatemanager.ui.activities.details.DetailsActivity;
import com.openclassrooms.realestatemanager.utils.AddModifyHouseHelper;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AddHouseActivity extends AppCompatActivity {

    private RecyclerView addNewHouseToDoList;
    private LinearLayoutManager layoutManager;
    private AddNewHouseAdapter adapter;
    private RealEstateManagerAPIService service;
    private List<String> textEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);
        //Initialize service
        service = DI.getService();

        setToolbar();
        //initialize recycler view
        addNewHouseToDoList = findViewById(R.id.new_house_list);
        layoutManager = new LinearLayoutManager(this);
        addNewHouseToDoList.setLayoutManager(layoutManager);
        adapter = new AddNewHouseAdapter(this);
        addNewHouseToDoList.setAdapter(adapter);
        //divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(addNewHouseToDoList.getContext(),
                layoutManager.getOrientation());
        addNewHouseToDoList.addItemDecoration(dividerItemDecoration);

        service.setActivity(this, "AddHouse");
    }

    private void setToolbar(){
        //initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_new_house);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_house_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                PhotoListAdapter.getAllPhotos().remove(PhotoListAdapter.getAddPhoto());
                return true;
            case R.id.confirm_add:
                if (checkAllData(AddModifyHouseHelper.getData())) {
                    getViewsAndAddHouse();
                } else {
                    setDialogErrorEmptyCases();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Check if all data is filled else return false to show AlertDialog
    private boolean checkAllData(List<EditText> data){
        textEmpty = new ArrayList<>();
        List<Photo> checkPhotos = PhotoListAdapter.getAllPhotos();
        checkPhotos.remove( PhotoListAdapter.getAddPhoto());
        int dataSize = 0;
        if (AddModifyHouseHelper.getHouse().getName() != "Select..."){
            dataSize++;
        } else {
            textEmpty.add("Name");
        }
        for (EditText e : data){
            if (!isEmpty(e)){
                dataSize++;
            } else {
                textEmpty.add(String.valueOf(e.getTag()));
            }
        }

        if (AddModifyHouseHelper.getHouse().isAvailable()){
            dataSize++;
        } else {
            textEmpty.add("Availability");
        }


        if (checkPhotos.size() >= 1){
            dataSize++;
        } else {
            textEmpty.add("Photos");
        }

        int totalSize = data.size() + 3;

        if (dataSize == totalSize) {
            return true;
        } else {
            if (!AddModifyHouseHelper.getPhotos().contains(PhotoListAdapter.getAddPhoto())) {
                AddModifyHouseHelper.getPhotos().add(PhotoListAdapter.getAddPhoto());
            }
            return false;
        }
    }

     // check if edit text is empty
    private boolean isEmpty(EditText editText){
        return editText.length() == 0|| editText.equals("");
    }

    /**
     * send error if any case is empty
     */
    private void setDialogErrorEmptyCases(){
        String unCompleteCases = TextUtils.join(", ", textEmpty);
        AlertDialog.Builder notifyError = new AlertDialog.Builder(this);
        notifyError.setCancelable(false);
        notifyError.setTitle("Error");
        notifyError.setMessage("Please fill these cases : " + unCompleteCases);
        notifyError.setIcon(R.drawable.ic_clear_black_24dp);

        notifyError.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = notifyError.create();
        alert.show();
    }

    /**
     * Get all data from add or modify house and send notification
     **/
    private void getViewsAndAddHouse() {

        final House house = AddModifyHouseHelper.getHouse();

        if (service.getUser() != null){
            house.setAgentId(service.getUser().getUserId());
        }
        service.addHouseToList(house, this);
        service.setHouse(house);
        AdressHouse adress = AddModifyHouseHelper.getAdressHouse();
        adress.setId(String.valueOf(house.getId()));
        adress.setHouseId(String.valueOf(house.getId()));
        service.addHousesDetails(AddModifyHouseHelper.getHouseDetails(), this);
        service.addAdresses(adress, this);
        for (int i = 0; i < PhotoListAdapter.getAllPhotos().size(); i++) {
            final Photo photo = PhotoListAdapter.getAllPhotos().get(i);
            if (!photo.getDescription().equals("Add new photo")) {
                Handler postHandler = new Handler();
                postHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        service.addPhotos(photo, getApplicationContext());
                        final FirebaseHelper helper = DI.getFirebaseDatabase();
                        helper.addPhotoToFirebase(photo, Uri.fromFile(new File(Utils.getRealPathFromURI(Uri.parse(photo.getPhotoUrl())))));
                        helper.addPhotoToFireStore(photo);
                    }
                }, 1000);
            }
        }

        Handler postHandler = new Handler();
        postHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNotification(house);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                startActivity(intent);
            }
        }, PhotoListAdapter.getAllPhotos().size() * 1000);

    }

    /**
     * code 100 for camera and 90 for media files
     * @param requestCode
     * @param resultCode
     * @param data
     */

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == 100) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 90);
        }

        if (requestCode == 90) {

            AlertDialog.Builder notifyNewPhoto = new AlertDialog.Builder(this);
            notifyNewPhoto.setCancelable(false);
            notifyNewPhoto.setTitle("Add a description");
            notifyNewPhoto.setMessage("What kind of room is it?");
            notifyNewPhoto.setIcon(R.drawable.ic_add_blue_24dp);

            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.house_description_dialog, null);
            final EditText descriptionText = view.findViewById(R.id.description_dialog);
            notifyNewPhoto.setView(view);

            notifyNewPhoto.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri imageUri = data.getData();
                    Photo photo;
                    photo = new Photo(imageUri.toString(), descriptionText.getText().toString(),
                            AddModifyHouseHelper.getHouse().getId());
                    photo.setId(UUID.randomUUID().toString());
                    AddModifyHouseHelper.getPhotos().add(photo);
                    adapter.notifyDataSetChanged();

                }
            });



            AlertDialog alert = notifyNewPhoto.create();
            alert.getWindow().setGravity(Gravity.BOTTOM);
            alert.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 105){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 100);
        } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 95){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 90);
        }
    }

    private void sendNotification(House house){
        String message = "You added " + house.getName() + " with success";
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.notif_icon)
                .setAutoCancel(true)
                .setContentTitle("Success ! ");
        builder.setStyle(new Notification.BigTextStyle()
                .bigText(message));

        NotificationManager notif = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(0, builder.build());
        PhotoListAdapter.getAllPhotos().clear();
    }

}

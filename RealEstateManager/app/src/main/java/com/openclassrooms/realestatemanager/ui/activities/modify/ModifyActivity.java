package com.openclassrooms.realestatemanager.ui.activities.modify;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.modify.ModifyAdapter;
import com.openclassrooms.realestatemanager.ui.activities.details.DetailsActivity;
import com.openclassrooms.realestatemanager.ui.adapters.modify.PhotoListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Almost the same as Add nEW New House Activity
public class ModifyActivity extends AppCompatActivity {

    private RecyclerView modifyHouseList;
    private LinearLayoutManager layoutManager;
    private  static ModifyAdapter  adapter;
    private RealEstateManagerAPIService service;
    private House house;
    private List<String> textEmpty;
    private List<Photo> houseImages;
    private HouseDetails details;
    private SaveToDatabase database = SaveToDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        service = DI.getService();

        Toolbar toolbar = findViewById(R.id.toolbar_modify_house);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        modifyHouseList = findViewById(R.id.modify_list);
        layoutManager = new LinearLayoutManager(this);
        modifyHouseList.setLayoutManager(layoutManager);
        List<Photo> photos = database.photoDao().getPhotos();
        houseImages = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++){
            if (photos.get(i).getHouseId().equals(String.valueOf(service.getHouse().getId()))){
                houseImages.add(photos.get(i));
            }
        }
        List<HouseDetails> houseDetailsList = database.houseDetailsDao().getDetails();
        for (HouseDetails houseDetails : houseDetailsList){
            if (houseDetails.getId().equals(service.getHouse().getId())){
                details = houseDetails;
            }
        }

        adapter = new ModifyAdapter(service.getHouse(), houseImages, details);
        modifyHouseList.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(modifyHouseList.getContext(),
                layoutManager.getOrientation());

        modifyHouseList.addItemDecoration(dividerItemDecoration);

        service.setActivity(this, "Modify");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
                if (checkAllData(ModifyAdapter.getData())) {
                    getViewsAndAddHouse();
                    Intent intent = new Intent(this, DetailsActivity.class);
                    startActivity(intent);
                } else {
                    setDialogErrorEmptyCases();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean checkAllData(List<EditText> data){
        int dataSize = 0;
        textEmpty = new ArrayList<>();
        List<Photo> checkPhotos = PhotoListAdapter.getAllPhotos();
        checkPhotos.remove(PhotoListAdapter.getAddPhoto());
        if (ModifyAdapter.gethouse().getName() != "Select..."){
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

        if ( checkPhotos.size() >= 1){
            dataSize++;
        } else {
            textEmpty.add("Photos");
        }

        int totalSize = data.size() + 2;

        if (dataSize == totalSize) {
            return true;
        } else {
            ModifyAdapter.getPhotos().add(PhotoListAdapter.getAddPhoto());
            return false;
        }
    }

    private boolean isEmpty(EditText editText){
        return editText.length() == 0|| editText.equals("");
    }

    private void getViewsAndAddHouse() {

        house = ModifyAdapter.gethouse();
        service.addHouseToList(house, this);
        for (int i = 0; i < ModifyAdapter.getPhotos().size(); i++){
            if (!database.photoDao().getPhotos().contains(ModifyAdapter.getPhotos().get(i))){
                Photo photo = ModifyAdapter.getPhotos().get(i);
                service.addPhotos(ModifyAdapter.getPhotos().get(i), this);
                FirebaseHelper helper = DI.getFirebaseDatabase();
                helper.addPhotoToFirebase(photo, Uri.fromFile(new File(getRealPathFromURI(Uri.parse(photo.getPhotoUrl())))));
                helper.addPhotoToFireStore(photo);
            }
        }
        service.addHousesDetails(ModifyAdapter.getDetails(), this);
        service.addAdresses(ModifyAdapter.getAdressHouse(), this);
        service.setHouse(house);
        sendNotification();

    }

    private String getRealPathFromURI(Uri contentURI) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }

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

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == 100) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 90);
        }

        if (requestCode == 90) {

            AlertDialog.Builder notifyNewPhoto = new AlertDialog.Builder(this);
            notifyNewPhoto.setCancelable(true);
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
                    Photo photo = new Photo(imageUri.toString(), descriptionText.getText().toString(),
                                String.valueOf(ModifyAdapter.gethouse().getId()));
                    photo.setId(UUID.randomUUID().toString());
                    ModifyAdapter.getPhotos().add(photo);
                    adapter.notifyDataSetChanged();
                }
            });

            notifyNewPhoto.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
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

    public static ModifyAdapter getAdapter(){
        return adapter;
    }

    private void sendNotification(){
        String message;
        String title;
        if (!house.isAvailable()){
            title = "Congratulations";
            message = "You sold " + house.getName() + " ,Bravo!";
        } else {
            title = "Success";
            message = "You modified " + house.getName() + " with success";
        }
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.notif_icon)
                .setAutoCancel(true)
                .setContentTitle(title);
        builder.setStyle(new Notification.BigTextStyle()
                .bigText(message));

        NotificationManager notif = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notif.notify(0, builder.build());
        PhotoListAdapter.getAllPhotos().clear();
    }

}


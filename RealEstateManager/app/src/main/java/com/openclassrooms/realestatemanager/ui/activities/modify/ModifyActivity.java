package com.openclassrooms.realestatemanager.ui.activities.modify;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.second.SecondActivity;
import com.openclassrooms.realestatemanager.ui.adapters.modify.ModifyAdapter;
import com.openclassrooms.realestatemanager.ui.adapters.modify.PhotoListAdapter;
import com.openclassrooms.realestatemanager.utils.AddModifyHouseHelper;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Almost the same as Add New House Activity
public class ModifyActivity extends AppCompatActivity {

    private RecyclerView modifyHouseList;
    private LinearLayoutManager layoutManager;
    private ModifyAdapter  adapter;
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 95);
        }

        service = DI.getService();

        setToolbar();

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

        details = database.houseDetailsDao().getDetailsWithHouseId(service.getHouse().getId());

        adapter = new ModifyAdapter(service.getHouse(), houseImages, details, this);
        modifyHouseList.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(modifyHouseList.getContext(),
                layoutManager.getOrientation());
        modifyHouseList.addItemDecoration(dividerItemDecoration);

        service.setActivity(this, "Modify");

    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_modify_house);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                try {
                    PhotoListAdapter.getAllPhotos().remove(PhotoListAdapter.getAddPhoto());
                } catch (Exception e){}
                AddModifyHouseHelper.setNull();
                return true;

            case R.id.confirm_add:
                if (checkAllData(AddModifyHouseHelper.getData())) {
                    getViewsAndAddHouse();
                    Intent intent = new Intent(this, SecondActivity.class);
                    startActivity(intent);
                } else {
                    setDialogErrorEmptyCases();
                }
                AddModifyHouseHelper.setNull();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean checkAllData(List<EditText> data){
        int dataSize = 0;
        textEmpty = new ArrayList<>();
        List<Photo> checkPhotos = null;
        if (PhotoListAdapter.getAllPhotos() != null){
        checkPhotos = PhotoListAdapter.getAllPhotos();
        checkPhotos.remove(PhotoListAdapter.getAddPhoto());
        }
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

        if (checkPhotos != null) {
            if (checkPhotos.size() >= 1) {
                dataSize++;
            } else {
                textEmpty.add("Photos");
            }
        }

        int totalSize = data.size() + 2;

        if (dataSize == totalSize || textEmpty.size() < 1) {
            return true;
        } else {
            if (PhotoListAdapter.getAllPhotos().contains(PhotoListAdapter.getAddPhoto())){
                PhotoListAdapter.getAllPhotos().remove(PhotoListAdapter.getAddPhoto());
            }
            return false;
        }
    }

    private boolean isEmpty(EditText editText){
        return editText.length() == 0|| editText.equals("");
    }

    private void getViewsAndAddHouse() {

        house = AddModifyHouseHelper.getHouse();
        service.addHouseToList(house, this);

        for (int i = 0; i < AddModifyHouseHelper.getPhotos().size(); i++){
            if (database.photoDao().getPhototWithId(AddModifyHouseHelper.getPhotos().get(i).getId()) == null){
                if (!AddModifyHouseHelper.getPhotos().get(i).getDescription().equals("Add new photo")) {
                    Photo photo = AddModifyHouseHelper.getPhotos().get(i);
                    service.addPhotos(AddModifyHouseHelper.getPhotos().get(i), this);
                    FirebaseHelper helper = DI.getFirebaseDatabase();
                    helper.addPhotoToFirebase(photo, Uri.fromFile(new File(getRealPathFromURI(Uri.parse(photo.getPhotoUrl())))));
                    helper.addPhotoToFireStore(photo);
                }
            }
        }
        service.addHousesDetails(AddModifyHouseHelper.getHouseDetails(), this);
        service.addAdresses(AddModifyHouseHelper.getAdressHouse(), this);
        service.setHouse(house);


    }

    private String getRealPathFromURI(Uri contentURI) {
        String filePath;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentURI, proj, null, null, null);
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
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 90);
        }

        if (requestCode == 90) {

            AlertDialog.Builder notifyNewPhoto = new AlertDialog.Builder(this);
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
                    try {
                        Uri imageUri = data.getData();
                        Photo photo = new Photo(Utils.getRealPathFromURI(Uri.parse(imageUri.toString())), descriptionText.getText().toString(),
                                String.valueOf(AddModifyHouseHelper.getHouse().getId()));
                        photo.setId(UUID.randomUUID().toString());
                        AddModifyHouseHelper.getPhotos().add(photo);
                        adapter.notifyDataSetChanged();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Photo empty", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 90);
                    }
                }
            });

            AlertDialog alert = notifyNewPhoto.create();
            alert.setCanceledOnTouchOutside(false);
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

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (PhotoListAdapter.getAllPhotos().contains(PhotoListAdapter.getAddPhoto())) {
                PhotoListAdapter.getAllPhotos().remove(PhotoListAdapter.getAddPhoto());
            }
        } catch (Exception e){}
    }

}


package com.openclassrooms.realestatemanager.firebase;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.dialog.Dialogs;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.utils.Utils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FirebaseService implements FirebaseHelper {

    private List<User> users;
    private List<House> houses;
    private static String USERS = "users";
    private static String ID = "id";
    private List<AdressHouse> adressHouses;
    private List<HouseDetails> detailsList;
    private List<Photo> photos;
    private List<String> photosArray;
    private boolean isHouseEmpty;
    private boolean isPhotosEmpty;

    @Override
    public void setUsersList(List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    @Override
    public List<House> getHouses() {
        return houses;
    }

    @Override
    public List<User> getUsersFromFireBase() {
        users = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = firebaseDatabase.getReference();
        databaseRef.child(USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("userId").getValue().toString();
                    String name = postSnapshot.child("name").getValue().toString();
                    String email = postSnapshot.child("email").getValue().toString();
                    String image = postSnapshot.child("photoUri").getValue().toString();
                    User user = new User(id, name, email, image);
                    if (!users.contains(user)) {
                        users.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return users;
    }

    @Override
    public List<House> getHouseFromFirebase() {


        houses = new ArrayList<>();
        isHouseEmpty = false;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("house");
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue().toString();
                    String name = postSnapshot.child("name").getValue().toString();
                    String agentId = postSnapshot.child("agentId").getValue().toString();
                    String available = postSnapshot.child("available").getValue().toString();
                    String monetarySystem = postSnapshot.child("monetarySystem").getValue().toString();
                    String price = postSnapshot.child("price").getValue().toString();
                    String measureUnity = postSnapshot.child("measureUnity").getValue().toString();
                    String pointsOfInterest = null;
                    if (postSnapshot.child("pointsOfInterest").exists()) {
                        pointsOfInterest = postSnapshot.child("pointsOfInterest").getValue().toString();
                    }
                    House house = new House(id,name, price, Boolean.parseBoolean(available), monetarySystem, measureUnity);
                    house.setPointsOfInterest(pointsOfInterest);
                    house.setAgentId(agentId);

                    houses.add(house);
                    Utils.compareHousesLists(house);

                }
                isHouseEmpty = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return houses;
    }


    @Override
    public void addUserToFireBase(User user) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("users");
        databaseRef.child(user.getUserId()).setValue(user);
        this.users.add(user);
    }

    @Override
    public void addHouseToFirebase(House house) {
        if (this.houses == null){
            this.houses = new ArrayList<>();
        }
        houses.add(house);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("house");
        databaseRef.child(String.valueOf(house.getId())).setValue(house);
    }

    @Override
    public void addAdressToFrirebase(AdressHouse adressHouse) {

        adressHouses = new ArrayList<>();
        adressHouses.add(adressHouse);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("adresses");
        databaseRef.child(adressHouse.getId()).setValue(adressHouse);


    }

    @Override
    public List<AdressHouse> getHousesAdresses() {
        return adressHouses;
    }

    @Override
    public List<AdressHouse> getHousesAdressesFromFirebase() {
        isHouseEmpty = false;
        adressHouses = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("adresses");
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue().toString();
                    String adress = postSnapshot.child("adress").getValue().toString();
                    String city = postSnapshot.child("city").getValue().toString();
                    String houseId = postSnapshot.child("houseId").getValue().toString();
                    String state = postSnapshot.child("state").getValue().toString();
                    String country = postSnapshot.child("country").getValue().toString();
                    String zipCode = postSnapshot.child("zipCode").getValue().toString();
                    AdressHouse adressHouse = new AdressHouse(id, houseId, adress, city, state, country, zipCode);

                        adressHouses.add(adressHouse);
                        Utils.compareAdressLists(adressHouse);

                }
                isHouseEmpty = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return adressHouses;
    }

    @Override
    public void addDetailsToFireBase(HouseDetails details) {
        this.detailsList = new ArrayList<>();
        this.detailsList.add(details);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("details");
        databaseRef.child(String.valueOf(details.getId())).setValue(details);
    }

    @Override
    public void getDetailsFromFireBase() {
        detailsList = new ArrayList<>();
        isHouseEmpty = false;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("details");
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue().toString();
                    String houseId = postSnapshot.child("houseId").getValue().toString();
                    String surface = postSnapshot.child("surface").getValue().toString();
                    String roomsNumber = postSnapshot.child("roomsNumber").getValue().toString();
                    String bathroomsNumber = postSnapshot.child("bathroomsNumber").getValue().toString();
                    String bedroomsNumber = postSnapshot.child("bedroomsNumber").getValue().toString();
                    String onLineDate = postSnapshot.child("onLineDate").getValue().toString();
                    String description = postSnapshot.child("description").getValue().toString();
                    HouseDetails details = new HouseDetails(id, houseId);
                    details.setOnLineDate(onLineDate);
                    details.setDescription(description);
                    details.setSurface(surface);
                    details.setRoomsNumber(roomsNumber);
                    details.setBathroomsNumber(bathroomsNumber);
                    details.setBedroomsNumber(bedroomsNumber);
                    String soldDate = null;
                    if(postSnapshot.child("soldDate").exists()){
                        soldDate= postSnapshot.child("soldDate").getValue().toString();
                        details.setSoldDate(soldDate);
                    }

                    detailsList.add(details);
                    Utils.compareDetailsLists(details);

                }
                isHouseEmpty = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public List<HouseDetails> getDetails() {
        return detailsList;
    }

    @Override
    public void addPhotoToFirebase(Photo photo, Uri uploadImage) {
        Photo newPhoto = new Photo(photo.getPhotoUrl(), photo.getDescription(), photo.getHouseId());
        newPhoto.setId(photo.getId());
        newPhoto.setPhotoUrl(uploadImage.getLastPathSegment().replaceAll("\\.", ","));
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = firebaseDatabase.getReference("photos");
        databaseRef.child(String.valueOf(newPhoto.getId())).setValue(newPhoto);
        photos.add(photo);
    }

    @Override
    public void addPhotoToFireStore(final Photo photo) {
        Uri uploadImage = Uri.fromFile(new File(photo.getPhotoUrl()));
        final File localFile = new File(Environment.getExternalStorageDirectory(), uploadImage.getLastPathSegment());
        if (!localFile.exists()) {
            final Dialogs dialogs = new Dialogs();
            dialogs.notificationUpload(photo, DI.getService().getActivity(),
                    0, 1
            );
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference photoRef = storageRef.child(uploadImage.getLastPathSegment());
            photoRef.putFile(uploadImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialogs.dismissUpload();
                    dialogs.sendNotification(SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().getHouseById(photo.getHouseId()),
                            DI.getService().getActivity());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialogs.dismissUpload();
                    Toast.makeText(DI.getService().getActivity(), "failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    String value = taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount();
                    dialogs.updateNotificationUpload(value);
                }
            });
        }
    }

    @Override
    public void removePhoto(final Photo photo) {
        if (photos.contains(photo)) {
            final File file  = new File(photo.getPhotoUrl());
            Uri deleteImage = Uri.fromFile(file);
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference photoRef = storageRef.child(deleteImage.getLastPathSegment());
            photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(DI.getService().getActivity(), "Success deleted", Toast.LENGTH_SHORT).show();
                    photos.remove(photo);
                        if(!photos.toString().contains(photo.getPhotoUrl())) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("photos");
                            reference.child(photo.getId()).removeValue();
                            file.delete();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DI.getService().getActivity(), "Failed delete", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public boolean isHousesTaskFinish() {
        return isHouseEmpty;
    }

    @Override
    public boolean isPhotoEmpty() {
        return isPhotosEmpty;
    }

    @Override
    public void getPhotosFromFirebase() {

        photosArray = new ArrayList<>();
        photos = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = firebaseDatabase.getReference("photos");
        databaseRef.orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    isPhotosEmpty = false;
                    String id = postSnapshot.child("id").getValue().toString();
                    final String houseId = postSnapshot.child("houseId").getValue().toString();
                    String description = postSnapshot.child("description").getValue().toString();
                    final String photoUrl = postSnapshot.child("photoUrl").getValue().toString().replaceAll(",", ".");
                    final Photo photo = new Photo(photoUrl, description, houseId);
                    photo.setId(id);
                    final File localFile = new File(Environment.getExternalStorageDirectory(), photo.getPhotoUrl());
                    if (!localFile.exists()) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        storageReference.child(photoUrl).getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                StorageReference urlRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString());
                                    urlRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            photo.setPhotoUrl(localFile.toString());
                                            photos.add(photo);
                                            Utils.comparePhotosLists(photo);
                                            isPhotosEmpty = true;
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DI.getService().getActivity(), "Failed Download", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        }
                                    });
                            }
                        });
                    }  else {
                        photo.setPhotoUrl(localFile.toString());
                        photos.add(photo);
                        Utils.comparePhotosLists(photo);
                        isPhotosEmpty = true;
                    }

                }
                if (houses.size() < 1) {
                    isPhotosEmpty = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public List<Photo> getPhotos() {
        return photos;
    }

}

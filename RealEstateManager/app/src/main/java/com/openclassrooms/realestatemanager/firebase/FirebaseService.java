package com.openclassrooms.realestatemanager.firebase;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
    private static String USER_ID = "userId";
    private static String USER_PHOTO = "userPhoto";
    private static String USERNAME = "userName";
    private static String USER_EMAIL = "userEmail";
    private List<AdressHouse> adressHouses;
    private List<HouseDetails> detailsList;
    private List<Photo> photos;
    private List<String> photosArray;

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
        if(users == null) {
            users = new ArrayList<>();
        }
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

        if (houses == null) {
            houses = new ArrayList<>();
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("house");
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
                    House house = new House(id,name, price, Boolean.parseBoolean(available), monetarySystem, measureUnity);
                    house.setAgentId(agentId);
                    if (!houses.contains(house)) {
                        houses.add(house);
                    }
                }
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
        if (adressHouses == null){
            adressHouses = new ArrayList<>();
        }
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
        if (adressHouses == null){
            adressHouses = new ArrayList<>();
        }
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
                    if (!adressHouses.contains(adressHouse)) {
                        adressHouses.add(adressHouse);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return adressHouses;
    }

    @Override
    public void addDetailsToFireBase(HouseDetails details) {
        if (this.detailsList == null){
            this.detailsList = new ArrayList<>();
        }
        this.detailsList.add(details);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("details");
        databaseRef.child(String.valueOf(details.getId())).setValue(details);
    }

    @Override
    public void getDetailsFromFireBase() {
        if (detailsList == null){
            detailsList = new ArrayList<>();
        }
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
                    if (!detailsList.contains(details)) {
                        detailsList.add(details);
                    }
                }
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
    public void addPhotoToFireStore(Photo photo) {
            Uri uploadImage = Uri.fromFile(new File(Utils.getRealPathFromURI(Uri.parse(photo.getPhotoUrl()))));
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference photoRef = storageRef.child(uploadImage.getLastPathSegment());
            photoRef.putFile(uploadImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(DI.getService().getActivity(), "success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DI.getService().getActivity(), "failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(DI.getService().getActivity(), taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void removePhoto(final Photo photo) {
        if (photos.contains(photo)) {
            Uri deleteImage = Uri.fromFile(new File(getRealPathFromURI(Uri.parse(photo.getPhotoUrl()))));
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference photoRef = storageRef.child(deleteImage.getLastPathSegment());
            photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(DI.getService().getActivity(), "Success deleted", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("photos");
                    reference.child(photo.getId()).removeValue();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DI.getService().getActivity(), "Failed delete", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private String getRealPathFromURI(Uri contentURI) {
        String filePath;
        Cursor cursor = DI.getService().getActivity().getContentResolver().query(contentURI, null, null, null, null);
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

    @Override
    public void getPhotosFromFirebase() {
        if (photosArray == null){
            photosArray = new ArrayList<>();
        }
        if (photos == null){
            photos = new ArrayList<>();
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = firebaseDatabase.getReference("photos");
        databaseRef.orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue().toString();
                    String houseId = postSnapshot.child("houseId").getValue().toString();
                    String description = postSnapshot.child("description").getValue().toString();
                    final String photoUrl = postSnapshot.child("photoUrl").getValue().toString().replaceAll(",", ".");
                    final Photo photo = new Photo(photoUrl, description, houseId);
                    photo.setId(id);
                    if (!photos.contains(photo)) {

                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        storageReference.child(photoUrl).getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                StorageReference urlRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString());
                                final File localFile = new File(Environment.getExternalStorageDirectory() + "/Pictures/", photo.getPhotoUrl());
                                if (!localFile.exists()) {
                                    urlRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            photo.setPhotoUrl(localFile.toString());
                                            Toast.makeText(DI.getService().getActivity(), "Download at " + photo.getPhotoUrl(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DI.getService().getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(DI.getService().getActivity(), "progress " + localFile.toString() +
                                                    "\n" + taskSnapshot.getBytesTransferred() + "/" + taskSnapshot.getTotalByteCount(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    photo.setPhotoUrl(localFile.toString());
                                }

                                photos.add(photo);
                            }
                        });
                    }
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

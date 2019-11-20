package com.openclassrooms.realestatemanager.ui.adapters.modify;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.utils.AddModifyHouseHelper;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder>  {

    private static List<Photo> photos;
    private static Photo addPhoto;
    private Context context;
    private RealEstateManagerAPIService service;

    public PhotoListAdapter(List<Photo> photos, String houseId, Context context ) {
        service = DI.getService();
        this.context = context;
        Uri photoUri = Uri.parse(context.getResources().getDrawable(R.drawable.ic_add_blue_24dp).toString());
        addPhoto = new Photo(photoUri.toString(), "Add new photo", houseId);
        addPhoto.setId("jjjj");
        if (photos.size() > 0) {
            for (int i = 0; i < photos.size(); i++){
                if (!photos.get(i).getDescription().equals("Add new photo")){
                    i++;
                    if (i == photos.size()){
                        photos.add(addPhoto);
                    }
                }
            }
        } else {
            photos.add(addPhoto);
        }
        this.photos = photos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_layout_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int i = position;

        if (!photos.get(position).getDescription().equals("Add new photo")) {
            Glide.with(holder.itemView.getContext()).load(photos.get(position).getPhotoUrl()).into(holder.houseImg);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.ic_add_blue_24dp).into(holder.houseImg);
        }

        holder.descriptionText.setText(photos.get(position).getDescription());
        holder.houseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.descriptionText.getText().equals("Add new photo")){
                    showDialogAddNewPhoto(holder);
                }
            }
        });

    }

    private void showDialogAddNewPhoto(ViewHolder viewHolder){
        context = viewHolder.itemView.getContext().getApplicationContext();
        AlertDialog.Builder notifyNewPhoto = new AlertDialog.Builder(viewHolder.itemView.getContext());
        notifyNewPhoto.setCancelable(true);
        notifyNewPhoto.setTitle("Add a new photo");
        notifyNewPhoto.setMessage("Where would you like to take it ?");
        notifyNewPhoto.setIcon(R.drawable.ic_add_blue_24dp);

        notifyNewPhoto.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    photos.remove(addPhoto);
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    service.getActivity().startActivityForResult(intent, 100);
                } else {
                    photos.remove(addPhoto);
                    ActivityCompat.requestPermissions(service.getActivity(), new String[]{Manifest.permission.CAMERA}, 105);
                }
            }
        });

        notifyNewPhoto.setNegativeButton("Files", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    photos.remove(addPhoto);
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    service.getActivity().startActivityForResult(intent, 90);
                } else {
                    photos.remove(addPhoto);
                    ActivityCompat.requestPermissions(service.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 95);
                }
            }
        });

        notifyNewPhoto.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = notifyNewPhoto.create();
        alert.getWindow().setGravity(Gravity.BOTTOM);
        alert.show();
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView houseImg;
        private TextView descriptionText;

        public ViewHolder(View itemView) {
            super(itemView);

            houseImg = itemView.findViewById(R.id.edit_img_view);
            descriptionText = itemView.findViewById(R.id.media_image_description_edit);
        }
    }

    public static Photo getAddPhoto(){
        return addPhoto;
    }

    public static List<Photo> getAllPhotos(){
        return photos;
    }
}

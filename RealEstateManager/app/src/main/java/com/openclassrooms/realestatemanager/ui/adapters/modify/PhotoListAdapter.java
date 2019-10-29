package com.openclassrooms.realestatemanager.ui.adapters.modify;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder>  {

    private List<Photo> photos;
    private Photo addPhoto;
    private Context context;

    public PhotoListAdapter(List<Photo> photos) {
        this.photos = photos;
        addPhoto = new Photo(R.drawable.ic_add_blue_24dp, "Add new photo");
        photos.add(addPhoto);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_layout_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
           holder.houseImg.setImageResource(photos.get(position).getPhotoUrl());
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
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    context.startActivity(intent);
                    notifyDataSetChanged();
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
                    context.startActivity(Intent.createChooser(intent, "Select Picture"));
                    notifyDataSetChanged();
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
}

package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.ui.activities.imageview.FullScreenImage;


import java.util.List;

public class MediaFragmentAdapter extends RecyclerView.Adapter<MediaFragmentAdapter.ViewHolderItem> {

    private List<Photo> photos;
    private Context context;
    private Photo photo;

    public MediaFragmentAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    @Override
    public MediaFragmentAdapter.ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_frag_layout,parent,false);
       ViewHolderItem   viewHolder = new ViewHolderItem(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MediaFragmentAdapter.ViewHolderItem holder, final int position) {

        photo = photos.get(position);

        Glide.with(holder.houseImg.getContext()).load(photo.getPhotoUrl()).into(holder.houseImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DI.getService().setPhoto(photos.get(position));
                Intent intent = new Intent(context, FullScreenImage.class);
                context.startActivity(intent);
            }
        });

        holder.imgDescription.setText(photo.getDescription());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    static class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView imgDescription;
        ImageView houseImg;

        public ViewHolderItem(View itemView) {
            super(itemView);

           imgDescription = itemView.findViewById(R.id.media_image_description);

            houseImg = itemView.findViewById(R.id.media_frag_img_view);
        }
    }
}

package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.events.DetailsEvent;
import com.openclassrooms.realestatemanager.events.ImageEvent;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.imageview.FullScreenImage;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

public class MediaFragmentAdapter extends BaseAdapter {

    private List<Photo> photos;
    private Context context;
    private Photo photo;

    public MediaFragmentAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int i) {
        return photos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolderItem viewHolder;
        photo = photos.get(i);

        if (view == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.media_frag_layout, viewGroup, false);

            viewHolder = new ViewHolderItem();
            viewHolder.imgDescription = view.findViewById(R.id.media_image_description);

            viewHolder.houseImg = view.findViewById(R.id.media_frag_img_view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        Glide.with(viewHolder.houseImg.getContext()).load(photo.getPhotoUrl()).into(viewHolder.houseImg);

        viewHolder.houseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DI.getService().setPhoto(photos.get(i));
                Intent intent = new Intent(context, FullScreenImage.class);
                context.startActivity(intent);
            }
        });

        viewHolder.imgDescription.setText(photos.get(i).getDescription());
        return view;
    }



    static class ViewHolderItem {
        TextView imgDescription;
        ImageView houseImg;
    }
}

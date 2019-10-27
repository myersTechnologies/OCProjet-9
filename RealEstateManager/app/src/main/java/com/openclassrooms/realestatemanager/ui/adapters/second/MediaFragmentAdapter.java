package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

public class MediaFragmentAdapter extends BaseAdapter {

    private List<Photo> photos;
    private Context context;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderItem viewHolder;

        if (view == null) {

            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.media_frag_layout, viewGroup, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.imgDescription = view.findViewById(R.id.media_image_description);

            viewHolder.houseImg = view.findViewById(R.id.media_frag_img_view);
            // store the holder with the view.
            view.setTag(viewHolder);


        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) view.getTag();
        }

        viewHolder.houseImg.setImageResource(photos.get(i).getPhotoUrl());
        viewHolder.imgDescription.setText(photos.get(i).getDescription());
        return view;
    }

    static class ViewHolderItem {
        TextView imgDescription;
        ImageView houseImg;
    }
}

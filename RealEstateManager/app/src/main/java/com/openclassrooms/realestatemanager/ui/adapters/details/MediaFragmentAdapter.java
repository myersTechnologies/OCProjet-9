package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;

import java.io.File;
import java.util.List;

public class MediaFragmentAdapter extends BaseAdapter {

    private List<Photo> photos;
    private Context context;
    private RealEstateManagerAPIService service;

    public MediaFragmentAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
        service = DI.getService();
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
        final ViewHolderItem viewHolder;

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

        try {
            String url = service.getRealPathFromUri(photos.get(i).getPhotoUrl());
            final File imageFile = new File(url);
            if (imageFile.exists()) {
                android.os.Handler loadingHandler = new Handler();
                loadingHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.houseImg.setImageBitmap(service.decodeSampledBitmapFromResource(null, imageFile, 100, 100));
                    }
                }, 100);
            }
        }catch (Exception e){}


        viewHolder.imgDescription.setText(photos.get(i).getDescription());
        return view;
    }



    static class ViewHolderItem {
        TextView imgDescription;
        ImageView houseImg;
    }
}

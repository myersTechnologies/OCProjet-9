package com.openclassrooms.realestatemanager.ui.adapters.analitycs;

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
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.ui.activities.details.DetailsActivity;

import java.util.List;

public class PhotoAnalyticsAdapter  extends RecyclerView.Adapter<PhotoAnalyticsAdapter.ViewHolder> {

    private List<House> myHouses;
    private SaveToDatabase database;
    private Context context;

    public PhotoAnalyticsAdapter(List<House> myHouses, Context context) {
        this.myHouses = myHouses;
        this.context = context;
        database = SaveToDatabase.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_layout_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        final House house = myHouses.get(position);
        List<Photo> photos = database.photoDao().getPhotos();
        for (int i = 0; i < photos.size(); i++){
            if (photos.get(i).getHouseId().equals(String.valueOf(house.getId()))){
                Glide.with(viewHolder.itemView.getContext()).load(photos.get(i).getPhotoUrl()).into(viewHolder.houseImg);
                break;
            }
        }
        viewHolder.houseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DI.getService().setHouse(house);
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            }
        });
        viewHolder.descriptionText.setText(myHouses.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return myHouses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView houseImg;
        private TextView descriptionText;
        private ImageView deleteImage;

        public ViewHolder(View itemView) {
            super(itemView);

            houseImg = itemView.findViewById(R.id.edit_img_view);
            descriptionText = itemView.findViewById(R.id.media_image_description_edit);
            deleteImage = itemView.findViewById(R.id.delete_img_view);
            deleteImage.setVisibility(View.INVISIBLE);
        }
    }

}

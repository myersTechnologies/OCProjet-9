package com.openclassrooms.realestatemanager.ui.adapters.analitycs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.ui.adapters.modify.PhotoListAdapter;

import java.util.List;

public class PhotoAnalyticsAdapter  extends RecyclerView.Adapter<PhotoAnalyticsAdapter.ViewHolder> {

    private List<House> myHouses;

    public PhotoAnalyticsAdapter(List<House> myHouses) {
        this.myHouses = myHouses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_layout_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        Glide.with(viewHolder.itemView.getContext()).load(myHouses.get(position).getImages().get(0).getPhotoUrl()).into(viewHolder.houseImg);
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

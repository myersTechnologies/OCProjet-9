package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.fragments.second.DetailsFragment;

import org.w3c.dom.Text;

public class DetailsFragmentAdapter extends RecyclerView.Adapter<DetailsFragmentAdapter.ViewHolder> {

    private House house;

    public DetailsFragmentAdapter(House house){
        this.house = house;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.description_details_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.descriptionText.setText(house.getDescription());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {

      private TextView descriptionText;

        private ViewHolder(View itemView) {
            super(itemView);

            descriptionText = itemView.findViewById(R.id.description_text);

        }
    }

}

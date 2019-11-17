package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.HouseDetails;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder>  {

    private HouseDetails details;

    public DescriptionAdapter(HouseDetails details) {
        this.details = details;
    }

    @NonNull
    @Override
    public DescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.description_layout, viewGroup, false);
        DescriptionViewHolder viewHolder = new DescriptionViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionViewHolder descriptionViewHolder, int i) {
        descriptionViewHolder.descriptionText.setText(details.getDescription());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class DescriptionViewHolder extends RecyclerView.ViewHolder{

        private TextView descriptionText;
        public DescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionText = itemView.findViewById(R.id.description_text);
        }
    }
}

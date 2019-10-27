package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class ListFragmentAdapter  extends RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>  {

    public ListFragmentAdapter(){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {


        private ViewHolder(View itemView) {
            super(itemView);


        }
    }

}

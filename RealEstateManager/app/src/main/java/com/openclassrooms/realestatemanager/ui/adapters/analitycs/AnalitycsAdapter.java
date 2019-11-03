package com.openclassrooms.realestatemanager.ui.adapters.analitycs;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.ui.adapters.addnewhouse.AddNewHouseAdapter;
import com.openclassrooms.realestatemanager.ui.adapters.modify.PhotoListAdapter;
import com.openclassrooms.realestatemanager.utils.Utils;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AnalitycsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<House> myHouses;
    private List<House> houses;

    public AnalitycsAdapter(List<House> myHouses, List<House> houses) {
        this.myHouses = myHouses;
        this.houses = houses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == 0 || viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_header_expendable_list,parent,false);
            viewHolder = new HeaderViewHolder(view);
        }

        if (viewType == 1 || viewType == 3){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_layout,parent,false);
            viewHolder = new StatusHouseViewHolder(view);
        }

        if (viewType == 4 || viewType == 5){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_myhouses_layout,parent,false);
            viewHolder = new ProgressViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderView, int position) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if (holderView.getItemViewType() == 0) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holderView;
            headerViewHolder.textView.setText("Your houses for sale :");
        }

        if (holderView.getItemViewType() == 1) {
           StatusHouseViewHolder holder = (StatusHouseViewHolder) holderView;
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext() , LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            List<House> saleHouses = new ArrayList<>();
            for (int i = 0; i < myHouses.size(); i++){
                if (myHouses.get(i).isAvailable()){
                    saleHouses.add(myHouses.get(i));
                }
            }
            PhotoAnalyticsAdapter adapter = new PhotoAnalyticsAdapter(saleHouses);
            holder.recyclerView.setAdapter(adapter);
        }
        if (holderView.getItemViewType() == 2){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holderView;
            headerViewHolder.textView.setText("Houses you sold  :");
        }
        if (holderView.getItemViewType() == 3) {
            StatusHouseViewHolder holder = (StatusHouseViewHolder) holderView;
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext() , LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            List<House> soldHouses = new ArrayList<>();
            for (int i = 0; i < myHouses.size(); i++){
                if (!myHouses.get(i).isAvailable()){
                    soldHouses.add(myHouses.get(i));
                }
            }
            PhotoAnalyticsAdapter adapter = new PhotoAnalyticsAdapter(soldHouses);
            holder.recyclerView.setAdapter(adapter);
        }
        if (holderView.getItemViewType() == 4){
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holderView;
            List<House> soldHouses = new ArrayList<>();
            for (int i = 0; i < myHouses.size(); i++){
                if (!myHouses.get(i).isAvailable()){
                    soldHouses.add(myHouses.get(i));
                }
            }
            progressViewHolder.textView.setText("You sold " + " " + soldHouses.size() + " Estates");
            progressViewHolder.progressBar.setProgress(soldHouses.size());
            progressViewHolder.progressBar.setMax(myHouses.size());
        }

        if (holderView.getItemViewType() == 5){
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holderView;
            int countTotal = 0;
            int countAllListSize = 0;
            for (int j =0; j < houses.size(); j++){
                countAllListSize = countAllListSize + Integer.parseInt(houses.get(j).getPrice().replaceAll(",", ""));
            }
            for (int i = 0; i < myHouses.size(); i++){
                if (!myHouses.get(i).isAvailable()){
                    countTotal = countTotal + Integer.parseInt(myHouses.get(i).getPrice().replaceAll(",", ""));
                }
            }
            progressViewHolder.progressBar.setMax(countAllListSize);
            progressViewHolder.textView.setText("For a total of " + formatter.format(countTotal) + "/" + formatter.format(countAllListSize));
            progressViewHolder.progressBar.setProgress(countTotal);
        }


    }

    @Override
    public int getItemViewType(int position) {
        int view = 0;
        switch (position){
            case 0:
                view = 0;
                return 0;
            case 1:
                view = 1;
                return 1;
            case 2:
                view = 2;
                return 2;
            case 3:
                view = 3;
                return 3;
            case 4:
                view = 4;
                return 4;
            case 5:
                view = 5;
                return 5;
        }
        return view;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_header);
        }
    }

    static class StatusHouseViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView recyclerView;

        public StatusHouseViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.photos_items_list);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar progressBar;
        private TextView textView;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            textView = itemView.findViewById(R.id.pb_title);
        }
    }

}

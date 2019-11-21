package com.openclassrooms.realestatemanager.ui.adapters.analitycs;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AnalitycsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<House> myHouses;
    private List<House> houses;

    private int LAYOUT_ONE = 0;
    private int LAYOUT_TWO = 1;
    private int LAYOUT_TREE = 2;
    private int LAYOUT_FOUR = 3;
    private int LAYOUT_FIVE = 4;
    private int LAYOUT_SIX = 5;
    private int COUNT = 6;

    public AnalitycsAdapter(List<House> myHouses, List<House> houses) {
        this.myHouses = myHouses;
        this.houses = houses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == LAYOUT_ONE || viewType == LAYOUT_TREE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_header_expendable_list,parent,false);
            viewHolder = new HeaderViewHolder(view);
        }

        if (viewType == LAYOUT_TWO || viewType == LAYOUT_FOUR){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_layout,parent,false);
            viewHolder = new StatusHouseViewHolder(view);
        }

        if (viewType == LAYOUT_FIVE || viewType == LAYOUT_SIX){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_myhouses_layout,parent,false);
            viewHolder = new ProgressViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderView, int position) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if (holderView.getItemViewType() == LAYOUT_ONE) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holderView;
            headerViewHolder.textView.setText("Your houses for sale :");
        }

        if (holderView.getItemViewType() == LAYOUT_TWO) {
           StatusHouseViewHolder holder = (StatusHouseViewHolder) holderView;
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext() , LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);
            List<House> saleHouses = new ArrayList<>();
            for (int i = 0; i < myHouses.size(); i++){
                if (myHouses.get(i).isAvailable()){
                    saleHouses.add(myHouses.get(i));
                }
            }
            PhotoAnalyticsAdapter adapter = new PhotoAnalyticsAdapter(saleHouses, holder.itemView.getContext());
            holder.recyclerView.setAdapter(adapter);
        }
        if (holderView.getItemViewType() == LAYOUT_TREE){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holderView;
            headerViewHolder.textView.setText("Houses you sold  :");
        }
        if (holderView.getItemViewType() == LAYOUT_FOUR) {
            StatusHouseViewHolder holder = (StatusHouseViewHolder) holderView;
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext() , LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);
            List<House> soldHouses = new ArrayList<>();
            for (int i = 0; i < myHouses.size(); i++){
                if (!myHouses.get(i).isAvailable()){
                    soldHouses.add(myHouses.get(i));
                }
            }
            PhotoAnalyticsAdapter adapter = new PhotoAnalyticsAdapter(soldHouses, holder.itemView.getContext());
            holder.recyclerView.setAdapter(adapter);
        }
        if (holderView.getItemViewType() == LAYOUT_FIVE){
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

        if (holderView.getItemViewType() == LAYOUT_SIX){
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holderView;
            int countTotal = 0;
            int countAllListSize = 0;
            House house = null;
            String totalHouses = null;
            for (int j =0; j < houses.size(); j++){
                house = houses.get(j);
                int price = Integer.parseInt(houses.get(j).getPrice().replaceAll(",", ""));
                String valeurBruteTotal =  formatter.format(price).replaceAll("\\s", "");
                int total = countAllListSize + Integer.parseInt(valeurBruteTotal);
                totalHouses =  getPriceWithMonetarySystem(String.valueOf(total), house, formatter);
                countAllListSize = countAllListSize + price;
            }
            House myHouse = null;
            String  totalOwn = null;
            for (int i = 0; i < myHouses.size(); i++){
                myHouse = myHouses.get(i);
                if (!myHouses.get(i).isAvailable()){
                    int price = Integer.parseInt(myHouses.get(i).getPrice().replaceAll(",", ""));
                    String myValeurBrute = formatter.format(price).replaceAll("\\s", "");
                    int total = countTotal + Integer.parseInt(myValeurBrute);
                    totalOwn = getPriceWithMonetarySystem(String.valueOf(total), myHouse, formatter);
                    countTotal = countTotal + price;
                }
            }

            progressViewHolder.progressBar.setMax(countAllListSize);
            if (totalOwn == null){
                totalOwn = "0";
            }
            progressViewHolder.textView.setText("For a total of " + totalOwn + "/" + totalHouses);
            progressViewHolder.progressBar.setProgress(countTotal);
        }


    }

    public static String getPriceWithMonetarySystem(String valeurBrute, House house, DecimalFormat formatter){
        RealEstateManagerAPIService service = DI.getService();
        String result = null;
        if (service.getPreferences().getMonetarySystem().equals("€") && house.getMonetarySystem().equals("$")) {
            String resultString = formatter.format(Utils.convertDollarToEuro(Integer.parseInt(valeurBrute)));
            String decimalReplacement = resultString.replaceAll("\\s", ",");
            result = "€" + " " + decimalReplacement;
        }
        if (service.getPreferences().getMonetarySystem().equals("$") && house.getMonetarySystem().equals("€")){
            String resultString = formatter.format(Utils.convertEuroToDollar(Integer.parseInt(valeurBrute)));
            String decimalReplacement = resultString.replaceAll("\\s", ",");
            result = "$ " + decimalReplacement;
        }
        if (service.getPreferences().getMonetarySystem().equals(house.getMonetarySystem())){
                String resultString = formatter.format((Integer.parseInt(valeurBrute)));
            String decimalReplacement = resultString.replaceAll("\\s", ",");
            result = house.getMonetarySystem() + " " + decimalReplacement;
        }
        return result;
    }


    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return LAYOUT_ONE;
            case 1:
                return LAYOUT_TWO;
            case 2:
                return LAYOUT_TREE;
            case 3:
                return LAYOUT_FOUR;
            case 4:
                return LAYOUT_FIVE;
            case 5:
                return LAYOUT_SIX;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        return COUNT;
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

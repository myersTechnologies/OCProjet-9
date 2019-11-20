package com.openclassrooms.realestatemanager.ui.adapters.search;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.SearchHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchPointsAdapter extends RecyclerView.Adapter<SearchPointsAdapter.PointsViewHolder> {

    List<Integer> points;
    List<String> pointsString;

    public SearchPointsAdapter() {
         points = SearchHelper.getPointsTypesImage();
         pointsString = SearchHelper.getPointsTypesString();
    }

    @NonNull
    @Override
    public PointsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.points_image_layout, viewGroup,false);
        PointsViewHolder viewHolder = new PointsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointsViewHolder pointsViewHolder, int position) {
        int point = points.get(position);
        String pointString = pointsString.get(position);
        if (!SearchHelper.getSearch().getPointsOfInterest().equals("none")){
            for (int i = 0; i < SearchHelper.getSearch().getPointsOfInterest().split(",").length; i++){
                String check = SearchHelper.getSearch().getPointsOfInterest().split(",")[i];
                if (pointString.equals(check)){
                    pointsViewHolder.imageView.setBackgroundColor(Color.GREEN);
                }
            }
        }
        pointsViewHolder.imageView.setImageResource(point);
        pointsViewHolder.pointText.setText(pointString);
        setOnImageClickListener(pointsViewHolder);
    }

    private void setOnImageClickListener(final PointsViewHolder viewHolder){
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = viewHolder.pointText.getText().toString();
                if (viewHolder.imageView.getBackground() == null) {
                    viewHolder.imageView.setBackgroundColor(Color.GREEN);
                    if (SearchHelper.getSearch().getPointsOfInterest().equals("none")){
                        SearchHelper.getSearch().setPointsOfInterest(type + ",");
                    } else {
                        if (!SearchHelper.getSearch().getPointsOfInterest().contains(type)){
                            String pointsOfInterest = SearchHelper.getSearch().getPointsOfInterest();
                            SearchHelper.getSearch().setPointsOfInterest(pointsOfInterest + type + ",");
                        }
                    }
                } else {
                    viewHolder.imageView.setBackground(null);
                    String [] remove = SearchHelper.getSearch().getPointsOfInterest().split(",");
                    List<String> types = new ArrayList<>();
                    for (int i = 0; i < remove.length; i++){
                        if (!remove[i].equals(type)) {
                            types.add(remove[i]);
                        }
                    }
                    String points = TextUtils.join(",", types);
                    SearchHelper.getSearch().setPointsOfInterest(points);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return points.size();
    }

    static class PointsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView pointText;
        public PointsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.points_image);
            pointText = itemView.findViewById(R.id.points_text);
        }
    }

}

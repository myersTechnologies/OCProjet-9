package com.openclassrooms.realestatemanager.ui.fragments.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.search.SearchAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private Toolbar toolbar;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        toolbar.getMenu().findItem(R.id.add).setVisible(false);
        toolbar.getMenu().findItem(R.id.search).setVisible(false);
        recyclerView = view.findViewById(R.id.search_rv);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new SearchAdapter(getContext());
        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onStop() {
        super.onStop();
        toolbar.getMenu().findItem(R.id.add).setVisible(true);
        toolbar.getMenu().findItem(R.id.search).setVisible(true);
    }
}

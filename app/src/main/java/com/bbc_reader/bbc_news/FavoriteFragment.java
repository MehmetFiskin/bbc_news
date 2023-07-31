package com.bbc_reader.bbc_news;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteFragment extends Fragment {

    private List<String> favoriteItemList;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the list of favorite items
        favoriteItemList = new ArrayList<>();

        // Load favorite items from SharedPreferences and add them to the list
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", 0);
        Set<String> favoriteSet = sharedPreferences.getStringSet("FavoriteList", new HashSet<>());
        favoriteItemList.addAll(favoriteSet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        // Set up the ListView and ArrayAdapter with custom layout
        ListView listViewFavorites = view.findViewById(R.id.listViewFavorites);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_favorite, R.id.textViewFavoriteTitle, favoriteItemList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);
                TextView textViewTitle = itemView.findViewById(R.id.textViewFavoriteTitle);
                TextView textViewURL = itemView.findViewById(R.id.textViewFavoriteURL);

                // Get the selected item's data from the list
                String selectedItem = favoriteItemList.get(position);
                // Split the selectedItem into separate elements based on the comma (',') delimiter
                String[] elements = selectedItem.split(",");

                // Check if the elements array contains at least 2 elements (title and URL)
                if (elements.length >= 2) {
                    // Set the title and URL in their respective TextViews
                    textViewTitle.setText("Title: " + elements[0]);
                    textViewURL.setText("URL: " + elements[1]);
                }

                return itemView;
            }
        };
        listViewFavorites.setAdapter(adapter);

        // Set click listener for each item in the ListView (optional)
        listViewFavorites.setOnItemClickListener((parent, view1, position, id) -> {
            // Handle item click event here (if needed)
            // For example, you can open the DetailFragment and pass the selected item's data as arguments
            String selectedItem = favoriteItemList.get(position);
            DetailFragment detailFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putString("selectedItem", selectedItem);
            detailFragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment).addToBackStack(null).commit();
        });

        return view;
    }
}

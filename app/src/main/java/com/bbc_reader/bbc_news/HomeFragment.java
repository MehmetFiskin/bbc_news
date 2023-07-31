package com.bbc_reader.bbc_news;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private ListView listView;
    private SearchView searchView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);
        searchView = view.findViewById(R.id.searchView);

        dataList = new ArrayList<>();

        // Load the saved data from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", 0);
        Set<String> itemList = sharedPreferences.getStringSet("ItemList", new HashSet<>());
        dataList.addAll(itemList);

        // Create a custom ArrayAdapter to display only the title in the ListView
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_title, R.id.textViewTitle, dataList);
        listView.setAdapter(adapter);

        // Set a click listener for the ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = dataList.get(position);
                Fragment fragment = new DetailFragment();
                Bundle args = new Bundle();
                args.putString("selectedItem", selectedItem);
                fragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Set a listener for the SearchView to filter the ListView items
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // Find the FloatingActionButton and set its click listener
        FloatingActionButton fabFavorite = view.findViewById(R.id.fabFavorite);
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the FavoriteFragment
                Fragment fragment = new FavoriteFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}

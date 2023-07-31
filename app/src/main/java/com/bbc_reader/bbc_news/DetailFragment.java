package com.bbc_reader.bbc_news;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Retrieve the selected item's title (or GUID) from the arguments bundle
        Bundle args = getArguments();
        if (args != null) {
            String selectedItem = args.getString("selectedItem", "");
            if (!selectedItem.isEmpty()) {
                // Split the selectedItem into separate elements based on the comma (',') delimiter
                String[] elements = selectedItem.split(",");

                // Display the selected item's detailed information here
                TextView textViewTitle = view.findViewById(R.id.textViewTitle);
                TextView textViewURL = view.findViewById(R.id.textViewURL);
                TextView textViewDescription = view.findViewById(R.id.textViewDescription);
                TextView textViewGUID = view.findViewById(R.id.textViewGUID);

                // Check if the elements array contains at least 4 elements
                if (elements.length >= 4) {
                    // Display each element in its corresponding TextView
                    textViewTitle.setText("Title: " + elements[0]);
                    textViewURL.setText("URL: " + elements[1]);
                    textViewDescription.setText("Description: " + elements[2]);

                    // Combine the remaining elements into a single string (in case there are commas in the description or GUID)
                    StringBuilder guidBuilder = new StringBuilder("GUID: ");
                    for (int i = 3; i < elements.length; i++) {
                        guidBuilder.append(elements[i]);
                        if (i < elements.length - 1) {
                            guidBuilder.append(",");
                        }
                    }
                    textViewGUID.setText(guidBuilder.toString());
                }

                // Find the "Delete" button and set its click listener
                Button deleteButton = view.findViewById(R.id.buttonDelete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Delete the selected item from SharedPreferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", 0);
                        Set<String> itemList = sharedPreferences.getStringSet("ItemList", new HashSet<>());
                        itemList.remove(selectedItem);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putStringSet("ItemList", itemList);
                        editor.apply();

                        // Navigate back to the HomeFragment after deletion
                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }
                    }
                });

                // Find the "Add to Favorite" button and set its click listener
                Button addToFavoriteButton = view.findViewById(R.id.buttonAddToFavorite);
                addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Save the selected item to the FavoriteList in SharedPreferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", 0);
                        Set<String> favoriteList = sharedPreferences.getStringSet("FavoriteList", new HashSet<>());
                        favoriteList.add(selectedItem);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putStringSet("FavoriteList", favoriteList);
                        editor.apply();

                        // Display a message indicating that the item has been added to favorites
                        Toast.makeText(getActivity(), "Added to Favorites!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        return view;
    }
}

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


        Bundle args = getArguments();
        if (args != null) {
            String selectedItem = args.getString("selectedItem", "");
            if (!selectedItem.isEmpty()) {

                String[] elements = selectedItem.split(",");


                TextView textViewTitle = view.findViewById(R.id.textViewTitle);
                TextView textViewURL = view.findViewById(R.id.textViewURL);
                TextView textViewDescription = view.findViewById(R.id.textViewDescription);
                TextView textViewGUID = view.findViewById(R.id.textViewGUID);


                if (elements.length >= 4) {

                    textViewTitle.setText("Title: " + elements[0]);
                    textViewURL.setText("URL: " + elements[1]);
                    textViewDescription.setText("Description: " + elements[2]);


                    StringBuilder guidBuilder = new StringBuilder("GUID: ");
                    for (int i = 3; i < elements.length; i++) {
                        guidBuilder.append(elements[i]);
                        if (i < elements.length - 1) {
                            guidBuilder.append(",");
                        }
                    }
                    textViewGUID.setText(guidBuilder.toString());
                }


                Button deleteButton = view.findViewById(R.id.buttonDelete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", 0);
                        Set<String> itemList = sharedPreferences.getStringSet("ItemList", new HashSet<>());
                        itemList.remove(selectedItem);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putStringSet("ItemList", itemList);
                        editor.apply();


                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }
                    }
                });


                Button addToFavoriteButton = view.findViewById(R.id.buttonAddToFavorite);
                addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", 0);
                        Set<String> favoriteList = sharedPreferences.getStringSet("FavoriteList", new HashSet<>());
                        favoriteList.add(selectedItem);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putStringSet("FavoriteList", favoriteList);
                        editor.apply();


                        Toast.makeText(getActivity(), "Added to Favorites!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        return view;
    }
}

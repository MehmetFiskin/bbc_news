package com.bbc_reader.bbc_news;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

public class AddSourceFragment extends Fragment {

    private EditText editTextTitle, editTextURL, editTextDescription, editTextGUID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addsource, container, false);

        // Initialize your EditText views
        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextURL = view.findViewById(R.id.editTextURL);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextGUID = view.findViewById(R.id.editTextGUID);

        // Find the "Add" button and set its click listener
        Button addButton = view.findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText views
                String title = editTextTitle.getText().toString();
                String url = editTextURL.getText().toString();
                String description = editTextDescription.getText().toString();
                String guid = editTextGUID.getText().toString();

                // Save the data to SharedPreferences as a list of items
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", 0);
                Set<String> itemList = sharedPreferences.getStringSet("ItemList", new HashSet<>());
                itemList.add(title + "," + url + "," + description + "," + guid);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("ItemList", itemList);
                editor.apply();

                // Show AlertDialog to inform the user that the item was added successfully
                showSuccessDialog();

                // Navigate back to HomeFragment
                navigateToHomeFragment();
            }
        });

        return view;
    }

    // Method to show the success dialog
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Success")
                .setMessage("Item added successfully!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to navigate back to HomeFragment
    private void navigateToHomeFragment() {
        // Replace the current fragment with the HomeFragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }
}

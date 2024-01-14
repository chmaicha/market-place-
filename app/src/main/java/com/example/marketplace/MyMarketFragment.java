package com.example.marketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marketplace.model.Marketplace;
import com.example.marketplace.model.UserSingleton;
import com.squareup.picasso.Picasso;


public class MyMarketFragment extends Fragment {
    private EditText editTextName;
    private EditText editTextImageUrl;
    private TextView textViewTitle;
    private ImageView imageView;
    private Button saveMarketButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_market, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextImageUrl = view.findViewById(R.id.editTextImageUrl);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        imageView = view.findViewById(R.id.imageView);

        // load initial data
        Marketplace marketplace = UserSingleton.getInstance().getCurrentUser().getMarketplace();
        editTextName.setText(marketplace.getName());
        editTextImageUrl.setText(marketplace.getImageUrl());

        saveMarketButton = view.findViewById(R.id.buttonSaveMarket);
        saveMarketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromUrl();
            }
        });

        loadImageFromUrl();



        return view;
    }

    private void loadImageFromUrl() {
        String imageUrl = editTextImageUrl.getText().toString().trim();

        if (!imageUrl.isEmpty()) {
            // Use Picasso to load the image into the ImageView
            Picasso.get().load(imageUrl).into(imageView);

            // Display the entered name as a title
            String enteredName = editTextName.getText().toString().trim();
            textViewTitle.setText(enteredName);
            saveMarketButton.setVisibility(View.VISIBLE);
        } else {
            // Handle the case where the image URL is empty
            Toast.makeText(requireContext(), "Image URL is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
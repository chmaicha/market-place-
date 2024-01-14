package com.example.marketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marketplace.dbStuff.MarketplaceDataSource;
import com.example.marketplace.dbStuff.ProductDataSource;
import com.example.marketplace.model.Marketplace;
import com.example.marketplace.model.Product;

import java.util.ArrayList;
import java.util.List;


public class MarketsFragment extends Fragment {
    private Spinner marketSpinner;
    private List<Product> productList = new ArrayList<>();
    private ProductDataSource productDataSource;
    private MarketplaceDataSource marketplaceDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productDataSource = new ProductDataSource(requireContext());
        productDataSource.open();
        marketplaceDataSource = new MarketplaceDataSource(requireContext());
        marketplaceDataSource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_markets, container, false);

        // Get the Spinner reference from the layout
        marketSpinner = view.findViewById(R.id.marketSpinner);

        // Get the markets list
        List<Marketplace> marketplaces = marketplaceDataSource.getAllMarketplaces();
        List<String> marketNamesList = new ArrayList<>();
        for (Marketplace marketplace : marketplaces) {
            marketNamesList.add(marketplace.getName());
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, marketNamesList);
        // Apply the adapter to the spinner
        marketSpinner.setAdapter(adapter);

        marketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the category selection
                String selectedCategory = marketNamesList.get(position);
                Toast.makeText(requireContext(), "Fetching data for selected market: " + selectedCategory, Toast.LENGTH_LONG).show();
                productList = productDataSource.getProductsByMarketplaceId(marketplaces.get(position).getId());
                Log.d("ROLLER", "onItemSelected-size: "+ productList.size());
                Log.d("ROLLER", "onItemSelected-id: "+ marketplaces.get(position).getId());
                ProductAdapter productAdapter = new ProductAdapter(productList);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(requireContext(), "Select a market!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Close the database connection in onDestroy
        if (productDataSource != null) {
            productDataSource.close();
        }
        if (marketplaceDataSource != null) {
            marketplaceDataSource.close();
        }

    }
}
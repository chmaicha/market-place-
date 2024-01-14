package com.example.marketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.example.marketplace.dbStuff.ProductDataSource;
import com.example.marketplace.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CategoriesFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener {

    private Spinner categorySpinner;
    private List<Product> productList = new ArrayList<>();
    private ProductDataSource productDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productDataSource = new ProductDataSource(requireContext());
        productDataSource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // Get the Spinner reference from the layout
        categorySpinner = view.findViewById(R.id.categorySpinner);

        // List the categories
        String[] categoryArray = {"Cars", "Houses", "Phones", "Laptops", "Furniture"};

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categoryArray);
        // Apply the adapter to the spinner
        categorySpinner.setAdapter(adapter);

        // Set a listener to handle item selection
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the category selection
                String selectedCategory = categoryArray[position];
                Toast.makeText(requireContext(), "Fetching data for selected category: " + selectedCategory, Toast.LENGTH_LONG).show();
                productList = productDataSource.getProductsByCategory(selectedCategory);
                Log.d("ROLLER", "onItemSelected: "+ productList.size());
                ProductAdapter productAdapter = new ProductAdapter(productList);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(requireContext(), "Select a category!", Toast.LENGTH_SHORT).show();
            }
        });

//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // Two columns
//        recyclerView.setAdapter(new CategoryAdapter(categoryArray, this));

        return view;
    }

    @Override
    public void onCategoryClick(String category) {
        // this is No longer used
        Toast.makeText(requireContext(), "Clicked: " + category, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Close the database connection in onDestroy
        if (productDataSource != null) {
            productDataSource.close();
        }

    }

}
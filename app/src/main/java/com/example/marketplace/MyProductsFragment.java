package com.example.marketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marketplace.dbStuff.ProductDataSource;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.UserSingleton;

import java.util.ArrayList;
import java.util.List;

public class MyProductsFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_my_products, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Add your logic to populate the productList with data (e.g., fetch data from SQLite)
        Log.d("ROLLER", "onCreateView: Hello");
        productList = productDataSource.getProductsByMarketplaceId(UserSingleton.getInstance().getCurrentUser().getMarketplaceId());
        Log.d("ROLLER", "onCreateView: "+ productList.size());

        ProductAdapter productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

        return view;
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
package com.example.marketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marketplace.dbStuff.MarketplaceDataSource;
import com.example.marketplace.dbStuff.ProductDataSource;
import com.example.marketplace.dbStuff.UserDataSource;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.UserSingleton;


public class AddProductFragment extends Fragment {
    private EditText editTextName;
    private EditText editTextImageUrl;
    private EditText editTextPrice;
    private Spinner spinnerCategory;
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
        View view =  inflater.inflate(R.layout.fragment_add_product, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextImageUrl = view.findViewById(R.id.editTextImageUrl);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);


        Button addProductButton = view.findViewById(R.id.buttonAddProduct);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String imageUrl = editTextImageUrl.getText().toString();
                String price = editTextPrice.getText().toString();
                String selectedCategory = spinnerCategory.getSelectedItem().toString();
                Log.d("product_category", selectedCategory);
                if (!name.isEmpty() && !imageUrl.isEmpty() && !price.isEmpty()) {
                    Product product = new Product(name, imageUrl, Double.parseDouble(price));
                    product.setCategory(selectedCategory);
                    product.setMarketplaceId(UserSingleton.getInstance().getCurrentUser().getMarketplaceId());
                    long inserted_id = productDataSource.insertProduct(product);
                    if (inserted_id != -1) {
                        Toast.makeText(requireContext(), "Product Added!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(requireContext(), "bro, fill the inputs!", Toast.LENGTH_SHORT).show();
                }


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

    }
}
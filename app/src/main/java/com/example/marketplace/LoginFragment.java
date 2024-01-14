package com.example.marketplace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marketplace.dbStuff.MarketplaceDataSource;
import com.example.marketplace.dbStuff.UserDataSource;
import com.example.marketplace.model.Marketplace;
import com.example.marketplace.model.User;
import com.example.marketplace.model.UserSingleton;


public class LoginFragment extends Fragment {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignup;
    private TextView textViewError;
    private UserDataSource userDataSource;
    private MarketplaceDataSource marketplaceDataSource;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an instance of UserDataSource and open the database connection
        userDataSource = new UserDataSource(requireContext());
        userDataSource.open();
        marketplaceDataSource = new MarketplaceDataSource(requireContext());
        marketplaceDataSource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonSignup = view.findViewById(R.id.buttonSignup);
        textViewError = view.findViewById(R.id.textViewError);

        buttonLogin.setOnClickListener(v -> {
            // Perform login logic here
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Example: Check if username and password are not empty
            if (!username.isEmpty() && !password.isEmpty()) {
                User retrievedUser = userDataSource.getUserByName(username);
                if (retrievedUser != null) {
                    // User found, check password
                    if (retrievedUser.getPassword().equals(password)) {
                        Log.d("Database", "User found: " + retrievedUser.getName());
                        UserSingleton.getInstance().setCurrentUser(retrievedUser);
                        textViewError.setVisibility(View.GONE);
                        if (reloadListener != null) {
                            reloadListener.onReload();
                        }
                        Toast.makeText(requireContext(), "Logged In!", Toast.LENGTH_SHORT).show();
                        navigateToAnotherFragment();
                    }else {
                        // Display error message
                        textViewError.setVisibility(View.VISIBLE);
                        textViewError.setText("Incorrect Username or Password.");
                    }
                }else{
                    // Display error message
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("Incorrect Username or Password.");
                }


                if (username.equals("ramo") && password.equals("1234")){
                    // Successful login, hide error message
                    textViewError.setVisibility(View.GONE);
                    UserSingleton.getInstance().setCurrentUser(new User("ramo","1234"));
                    if (reloadListener != null) {
                        reloadListener.onReload();
                    }
                    Toast.makeText(requireContext(), "Logged In!", Toast.LENGTH_SHORT).show();
                    navigateToAnotherFragment();
                }else{
                    // Display error message
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("Incorrect Username or Password.");
                }
            } else {
                // Display error message
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText("Please enter both username and password.");
            }
        });

        buttonSignup.setOnClickListener(v -> {
            // Perform signup logic here
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Example: Check if username and password are not empty
            if (!username.isEmpty() && !password.isEmpty()) {
                // create marketplace then create user
                Marketplace new_marketplace = new Marketplace(
                        username + "'s market",
                        "https://images.pexels.com/photos/942320/pexels-photo-942320.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                );
                long inserted_market_id = marketplaceDataSource.insertMarketplace(new_marketplace);
                if (inserted_market_id != -1) {
                    // market created
                    User new_user = new User(username, password);
                    new_user.setMarketplaceId(inserted_market_id);
                    long insertedId = userDataSource.insertUser(new_user);
                    // Verify the result
                    if (insertedId != -1) {
                        // Successfully inserted
                        Log.d("Database", "User inserted with ID: " + insertedId);
                        User retrievedUser = userDataSource.getUserByName(username);
                        if (retrievedUser != null) {
                            // User found, do something with the user data
                            Log.d("Database", "User found: " + retrievedUser.getName());
                            UserSingleton.getInstance().setCurrentUser(retrievedUser);
                            textViewError.setVisibility(View.GONE);
                            if (reloadListener != null) {
                                reloadListener.onReload();
                            }
                            Toast.makeText(requireContext(), "Signed Up!", Toast.LENGTH_SHORT).show();
                            navigateToAnotherFragment();
                        } else {
                            // User not found
                            Log.d("Database", "User not found with username: " + username);
                            // Display error message
                            textViewError.setVisibility(View.VISIBLE);
                            textViewError.setText("an error happend, try again.");
                        }
                    } else {
                        // Insert failed
                        Log.e("Database", "Failed to insert Marketplace");
                        // Display error message
                        textViewError.setVisibility(View.VISIBLE);
                        textViewError.setText("an error happend, try again.");
                    }
                }else {
                    // Insert failed
                    Log.e("Database", "Failed to insert Marketplace");
                    // Display error message
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("an error happend, try again.");
                }


            } else {
                // Display error message
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText("Please enter both username and password.");
            }
        });

        return view;
    }

    private void navigateToAnotherFragment() {
        // Replace the current fragment with another one
        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new CategoriesFragment()) // Replace with the fragment you want to navigate to
                    .addToBackStack(null) // Optional: Add to the back stack
                    .commit();
        }
    }

    public interface ReloadActivityListener {
        void onReload();
    }

    private ReloadActivityListener reloadListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ReloadActivityListener) {
            reloadListener = (ReloadActivityListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement ReloadActivityListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Close the database connection in onDestroy
        if (userDataSource != null) {
            userDataSource.close();
        }
        if (marketplaceDataSource != null) {
            marketplaceDataSource.close();
        }
    }
}
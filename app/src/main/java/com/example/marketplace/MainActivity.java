package com.example.marketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marketplace.model.UserSingleton;
import com.google.android.material.navigation.NavigationView;
import com.example.marketplace.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.ReloadActivityListener {

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // The menu
        Menu navMenu = navigationView.getMenu();
        MenuItem loginItem = navMenu.findItem(R.id.nav_login);
        MenuItem logoutItem = navMenu.findItem(R.id.nav_logout);
        MenuItem myMarketItem = navMenu.findItem(R.id.nav_my_market);
        MenuItem myProductsItem = navMenu.findItem(R.id.nav_my_products);
        MenuItem addProductItem = navMenu.findItem(R.id.nav_add_product);

        if (UserSingleton.getInstance().isLoggedIn()) {
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
            myMarketItem.setVisible(true);
            myProductsItem.setVisible(true);
            addProductItem.setVisible(true);
            View headerView = navigationView.getHeaderView(0);
            TextView headerTitle = headerView.findViewById(R.id.nav_header_title);
            TextView headerSubtitle = headerView.findViewById(R.id.nav_header_subtitle);
            headerTitle.setText(UserSingleton.getInstance().getCurrentUser().getName());
            headerSubtitle.setText("Welcome back, " + UserSingleton.getInstance().getCurrentUser().getName() + "!");
        } else {
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
            myMarketItem.setVisible(false);
            myProductsItem.setVisible(false);
            addProductItem.setVisible(false);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;

            case R.id.nav_categories:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CategoriesFragment()).commit();
                break;

            case R.id.nav_markets:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MarketsFragment()).commit();
                break;

            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
                break;

            case R.id.nav_my_market:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyMarketFragment()).commit();
                break;
            case R.id.nav_my_products:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyProductsFragment()).commit();
                break;
            case R.id.nav_add_product:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddProductFragment()).commit();
                break;

            case R.id.nav_logout:
                UserSingleton.getInstance().logout();
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                // Replace the current fragment with another one
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new LoginFragment()) // Replace with the fragment you want to navigate to
                        .addToBackStack(null) // Optional: Add to the back stack
                        .commit();
                recreate();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onReload() {
        recreate();
    }
}
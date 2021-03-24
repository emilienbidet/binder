package com.emilienbidet.binderstpatrick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.binderstpatrick.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNav.setOnNavigationItemReselectedListener(navigationItemReselectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BinderFragment()).commit();
        bottomNav.setSelectedItemId(R.id.nav_binder);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("item", bottomNav.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        bottomNav.setSelectedItemId(savedInstanceState.getInt("item"));
        Fragment selectedFragment = new BinderFragment();
        switch (savedInstanceState.getInt("item")) {
            case R.id.nav_options:
                selectedFragment = new OptionsFragment();
                break;
            case R.id.nav_binder:
                selectedFragment = new BinderFragment();
                break;
            case R.id.nav_favorites:
                selectedFragment = new FavoritesFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemReselectedListener navigationItemReselectedListener =
            menuItem -> {
            };
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            menuItem -> {
                Fragment selectedFragment = new BinderFragment();
                switch (menuItem.getItemId()) {
                    case R.id.nav_options:
                        selectedFragment = new OptionsFragment();
                        break;
                    case R.id.nav_binder:
                        selectedFragment = new BinderFragment();
                        break;
                    case R.id.nav_favorites:
                        selectedFragment = new FavoritesFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                return true;
            };
}
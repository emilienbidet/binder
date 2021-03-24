package com.example.binderstpatrick;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

import static com.example.binderstpatrick.SharedPreferencesConfig.*;

public class FavoritesFragment extends Fragment {

    private ArrayList<Beer> favoriteBeers;
    private ListView lvBeers;

    public FavoritesFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
        lvBeers = view.findViewById(R.id.lv_favorites_beers);

        FavoriteBeersAdapter adapter= new FavoriteBeersAdapter(getContext(), favoriteBeers);
        lvBeers.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_BEERS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(SHARED_PREFERENCES_KEY_FAVORITE_BEERS, gson.toJson(favoriteBeers));
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_BEERS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Beer>>() {}.getType();
        favoriteBeers = gson.fromJson(sharedPreferences.getString(SHARED_PREFERENCES_KEY_FAVORITE_BEERS, null), type);
        if (favoriteBeers == null) {
            favoriteBeers = new ArrayList<>();
        }
    }
}

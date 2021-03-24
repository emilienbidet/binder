package com.example.binderstpatrick;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

import static com.example.binderstpatrick.SharedPreferencesConfig.*;
import static android.content.Context.MODE_PRIVATE;

public class BinderFragment extends Fragment {

    public BinderFragment() {
        super();
    }

    private ArrayList<Beer> favoriteBeers;
    private ArrayList<Beer> beers;
    private RecyclerView recyclerView;
    private int page;

    private Filters filters;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_binder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        page = 1;
        loadData();

        recyclerView = view.findViewById(R.id.rec_beers);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new BinderAdapter(this.getActivity(), this.beers));

        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(), beers);
        cardCallback.setOnSwipedListener(swipeListener);
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView, touchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(recyclerView);

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_BEERS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(SHARED_PREFERENCES_KEY_FAVORITE_BEERS, gson.toJson(favoriteBeers));
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_OPTIONS_NAME, 0);
        String json = sharedPreferences.getString(SHARED_PREFERENCES_KEY_FILTERS, null);
        filters = json != null ? new Gson().fromJson(json, Filters.class) : new Filters();

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_BEERS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Beer>>() {}.getType();
        beers = new ArrayList<>();
        getBeers();
        favoriteBeers = gson.fromJson(sharedPreferences.getString(SHARED_PREFERENCES_KEY_FAVORITE_BEERS, null), type);
        if (favoriteBeers == null) {
            favoriteBeers = new ArrayList<>();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    private void getBeers() {
        String url = "https://api.punkapi.com/v2/beers?per_page=10";
        url += Filters.PAGE + page;
        if (filters.getName().length() > 0) {
            url += Filters.BEER_NAME + filters.getName();
        }
        url += Filters.ABV_GT + filters.getAbvMin();
        url += Filters.ABV_LT + filters.getAbvMax();
        url += Filters.IBU_GT + filters.getIbuMin();
        url += Filters.IBU_LT + filters.getIbuMax();
        url += Filters.EBC_GT + filters.getEbcMin();
        url += Filters.EBC_LT + filters.getEbcMax();
        Ion.with(getContext())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        final GsonBuilder builder = new GsonBuilder();
                        final Gson gson = builder.create();
                        ArrayList<Beer> beerse  = gson.fromJson(result, new TypeToken<List<Beer>>(){}.getType());
                        beers.addAll(beerse);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });

    }

    private OnSwipeListener swipeListener = new OnSwipeListener<Beer>() {

        @Override
        public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
            /**
             * will callback when the card are swiping by user
             * viewHolder : thee viewHolder of swiping card
             * ratio : the ratio of swiping , you can add some animation by the ratio
             * direction : CardConfig.SWIPING_LEFT means swiping from left；CardConfig.SWIPING_RIGHT means swiping from right
             *             CardConfig.SWIPING_NONE means not left nor right
             */
            BinderAdapter.BeersViewHolder holder = (BinderAdapter.BeersViewHolder) viewHolder;
            viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
            if (direction == 8) {
                holder.likeImageView.setAlpha(Math.abs(ratio));
            } else if (direction == 4) {
                holder.dislikeImageView.setAlpha(Math.abs(ratio));
            } else  {
                holder.dislikeImageView.setAlpha(0f);
                holder.likeImageView.setAlpha(0f);
            }
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, Beer beer, int direction) {
            /**
             *  will callback when the card swiped from screen by user
             *  you can also clean animation from the itemview of viewHolder in this method
             *  viewHolder : the viewHolder of swiped cards
             *  t : the data of swiped cards from dataList
             *  direction : CardConfig.SWIPED_LEFT means swiped from left；CardConfig.SWIPED_RIGHT means swiped from right
             */
            Log.d("WSH", "onSwiped: "+ beer.getImageUrl());
            recyclerView.getAdapter().notifyDataSetChanged();
            BinderAdapter.BeersViewHolder holder = (BinderAdapter.BeersViewHolder) viewHolder;
            viewHolder.itemView.setAlpha(1f);
            holder.dislikeImageView.setAlpha(0f);
            holder.likeImageView.setAlpha(0f);
            if (direction == CardConfig.SWIPED_RIGHT) {
                if (!favoriteBeers.contains(beer)) {
                    favoriteBeers.add(beer);
                }
            }
            if (beers.size() < 5) {
                page += 1;
                getBeers();
            }
        }

        @Override
        public void onSwipedClear() {
            Toast.makeText(getContext(), "no more beers ", Toast.LENGTH_SHORT).show();
        }

    };
}

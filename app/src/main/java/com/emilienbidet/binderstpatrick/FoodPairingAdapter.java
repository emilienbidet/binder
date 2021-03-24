package com.emilienbidet.binderstpatrick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.binderstpatrick.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FoodPairingAdapter extends ArrayAdapter<String> {

    public FoodPairingAdapter(Context context, ArrayList<String> foodpairings) {
        super(context, 0, foodpairings);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String foodpairing = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_details_foodpairing, parent, false);
        }

        ImageView ivAvatar = convertView.findViewById(R.id.iv_food_pairing_avatar);
        TextView tvName = convertView.findViewById(R.id.tv_food_pairing_name);

        tvName.setText(foodpairing);
        try {
            setImageViewFromSearch(foodpairing, ivAvatar);
        } catch (UnsupportedEncodingException e) {

        }
        return convertView;
    }

    private void setImageViewFromSearch(String search, ImageView imageView) throws UnsupportedEncodingException {
        String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyDaG6fq2Qem0DaGuYnwlEMcQyQij1VKlTM&cx=c989d21b9463210ae&searchType=image&num=1&q=" + URLEncoder.encode(search, StandardCharsets.UTF_8.toString());
        Ion.with(getContext())
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            String link = result.getAsJsonArray("items").get(0).getAsJsonObject().get("link").getAsString();
                            Picasso.get().load(link).into(imageView);
                        } catch (NullPointerException exe) {
                            imageView.setImageResource(R.drawable.food_pairing);
                        }


                    }
                });

    }
}

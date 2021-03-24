package com.emilienbidet.binderstpatrick;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.binderstpatrick.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteBeersAdapter extends ArrayAdapter<Beer> {

    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvTagline;
    private TextView tvAbv;
    private TextView tvIbu;
    private TextView tvEbc;
    private ImageButton ibFavorite;
    private ConstraintLayout clInformation;

    private ArrayList<Beer> favoritebeers;

    public FavoriteBeersAdapter(@NonNull Context context, ArrayList<Beer> favoritebeers) {
        super(context, 0, favoritebeers);
        this.favoritebeers = favoritebeers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Beer beer = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_favorite_beers_beers, parent, false);
        }

        ivAvatar = convertView.findViewById(R.id.iv_favorite_beers_avatar);
        tvName = convertView.findViewById(R.id.tv_favorite_beers_name);
        tvTagline = convertView.findViewById(R.id.tv_favorite_beers_tagline);
        tvAbv = convertView.findViewById(R.id.tv_favorite_beers_abv);
        tvIbu = convertView.findViewById(R.id.tv_favorite_beers_ibu);
        tvEbc = convertView.findViewById(R.id.tv_favorite_beers_ebc);
        ibFavorite = convertView.findViewById(R.id.ib_favorite_beers_remove);
        clInformation = convertView.findViewById(R.id.cl_favorite_beers_information_container);

        if (beer.getImageUrl() != null) {
            Picasso.get().load(beer.getImageUrl()).into(ivAvatar);
        } else {
            ivAvatar.setImageResource(R.drawable.beer);
        }
        tvName.setText(beer.getName());
        tvTagline.setText(beer.getTagline());
        tvAbv.setText("ABV " + beer.getAbv());
        tvIbu.setText("IBU " + beer.getIbu());
        tvEbc.setText("EBC " + beer.getEbc());
        ibFavorite.setOnClickListener(favoriteImageButtonClickListener(ibFavorite, beer));
        clInformation.setOnClickListener(constraintLayoutClickListener(beer));

        return convertView;
    }


    private View.OnClickListener favoriteImageButtonClickListener(ImageButton imageButton, Beer beer) {
        return v -> {
            favoritebeers.remove(beer);
            notifyDataSetChanged();
        };
    }

    private View.OnClickListener constraintLayoutClickListener(Beer beer) {
        return v -> {
            Intent intent = new Intent(getContext(), BeerDetailsActivity.class);
            intent.putExtra("beer", (Parcelable) beer);
            getContext().startActivity(intent);
        };
    }
}

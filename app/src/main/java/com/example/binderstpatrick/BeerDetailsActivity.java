package com.example.binderstpatrick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BeerDetailsActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvTagline;
    private ImageView ivAvatar;
    private TextView tvAbv;
    private TextView tvIbu;
    private TextView tvEbc;
    private TextView tvDescription;
    private ListView lvPairing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        Intent myIntent = getIntent();
        Bundle bundle = myIntent.getExtras();
        Beer beer = bundle.getParcelable("beer");

        tvName = findViewById(R.id.tv_details_name);
        tvTagline = findViewById(R.id.tv_details_tagline);
        ivAvatar = findViewById(R.id.tv_details_avatar);
        tvAbv = findViewById(R.id.tv_details_abv);
        tvIbu = findViewById(R.id.tv_details_ibu);
        tvEbc = findViewById(R.id.tv_details_ebc);
        tvDescription = findViewById(R.id.tv_details_description);
        lvPairing = findViewById(R.id.lv_details_pairing);

        tvName.setText(beer.getName());
        tvTagline.setText(beer.getTagline());
        tvAbv.setText("ABV " + beer.getAbv());
        tvIbu.setText("IBU " + beer.getIbu());
        tvEbc.setText("EBC " + beer.getEbc());
        tvDescription.setText(beer.getDescription());
        if (beer.getImageUrl() != null) {
            Picasso.get().load(beer.getImageUrl()).into(ivAvatar);
        } else {
            ivAvatar.setImageResource(R.drawable.beer);
        }

        FoodPairingAdapter adapter= new FoodPairingAdapter(this, beer.getFoodPairing());
        lvPairing.setAdapter(adapter);
    }
}
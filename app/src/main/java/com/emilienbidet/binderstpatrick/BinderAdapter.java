package com.emilienbidet.binderstpatrick;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binderstpatrick.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BinderAdapter extends RecyclerView.Adapter {

    private ArrayList<Beer> beers;
    private Activity activity;

    public BinderAdapter(Activity activity, ArrayList<Beer> beers) {
        this.activity = activity;
        this.beers = beers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_binder_beers, parent, false);
        return new BeersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BeersViewHolder beersViewHolder = (BeersViewHolder) holder;
        Beer currentBeer = beers.get(position);

        beersViewHolder.textRelativeLayout.setOnClickListener(textRelativeLayoutClickListener(position));

        ImageView avatarImageView = beersViewHolder.avatarImageView;
        if (currentBeer.getImageUrl() != null) {
            Picasso.get().load(currentBeer.getImageUrl()).into(avatarImageView);
        } else {
            avatarImageView.setImageResource(R.drawable.beer);
        }

        beersViewHolder.nameTextView.setText(currentBeer.getName());
        beersViewHolder.abvTextView.setText("ABV " + currentBeer.getAbv());
        beersViewHolder.ibuTextView.setText("IBU " + currentBeer.getIbu());
        beersViewHolder.ebcTextView.setText("EBC " + currentBeer.getEbc());
        beersViewHolder.taglineTextView.setText(currentBeer.getTagline());
    }

    private View.OnClickListener textRelativeLayoutClickListener(int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BeerDetailsActivity.class);
                intent.putExtra("beer", (Parcelable) beers.get(position));
                activity.startActivity(intent);
            }
        };
    }


    @Override
    public int getItemCount() {
        return beers.size();
    }

    public class BeersViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout textRelativeLayout;

        ImageView avatarImageView;
        ImageView likeImageView;
        ImageView dislikeImageView;

        TextView nameTextView;
        TextView abvTextView;
        TextView ibuTextView;
        TextView ebcTextView;
        TextView taglineTextView;

        public BeersViewHolder(@NonNull View itemView) {
            super(itemView);

            textRelativeLayout = itemView.findViewById(R.id.rl_binder_text);

            avatarImageView = itemView.findViewById(R.id.iv_avatar);
            likeImageView = itemView.findViewById(R.id.iv_like);
            dislikeImageView = itemView.findViewById(R.id.iv_dislike);

            nameTextView = itemView.findViewById(R.id.tv_binder_name);
            abvTextView = itemView.findViewById(R.id.tv_binder_abv);
            ibuTextView = itemView.findViewById(R.id.tv_binder_ibu);
            ebcTextView = itemView.findViewById(R.id.tv_binder_ebc);
            taglineTextView = itemView.findViewById(R.id.tv_binder_tagline);
        }
    }
}

package com.example.firstproject_eat.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firstproject_eat.R;
import com.example.firstproject_eat.datamodels.Restaurant;
import com.example.firstproject_eat.ui.activities.ShopActivity;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private ArrayList<Restaurant> data;
    private Context context;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_restaurant, viewGroup, false);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RestaurantViewHolder vh = (RestaurantViewHolder) viewHolder;
        Restaurant item = data.get(position);

        vh.restaurantName.setText(item.getNome());
        vh.restaurantAddress.setText(item.getIndirizzo());
        vh.restaurantMinOrdine.append(String.valueOf(item.getMinOrdine()));

        Glide.with(context).load(item.getAnteprima()).into(vh.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView restaurantImage;
        public TextView restaurantName, restaurantAddress, restaurantMinOrdine;
        public Button menuBtn;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantImage = itemView.findViewById(R.id.restaurant_iv);
            restaurantName = itemView.findViewById(R.id.name_tv);
            restaurantAddress = itemView.findViewById(R.id.address_tv);
            restaurantMinOrdine = itemView.findViewById(R.id.min_ordine_tv);
            menuBtn = itemView.findViewById(R.id.menu_btn);

            menuBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.menu_btn){
                context.startActivity(new Intent(context, ShopActivity.class));

                //TODO intent;
            }
        }
    }
}

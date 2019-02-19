package com.example.firstproject_eat.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firstproject_eat.R;
import com.example.firstproject_eat.datamodels.Restaurant;
import com.example.firstproject_eat.ui.activities.ShopActivity;

import java.util.ArrayList;
import java.util.Locale;

public class RestaurantAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private ArrayList<Restaurant> data;
    private Context context;
    private boolean isGridMode;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public RestaurantAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        this.context = context;
    }

    public void setData(ArrayList<Restaurant> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public boolean isGridMode() {
        return isGridMode;
    }

    public void setGridMode(boolean gridMode) {
        isGridMode = gridMode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int layout;

        if(isGridMode){
            layout = R.layout.item_restaurant_grid;
        }else{
            layout = R.layout.item_restaurant;
        }

        View view = inflater.inflate(layout, viewGroup, false);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RestaurantViewHolder vh = (RestaurantViewHolder) viewHolder;
        Restaurant item = data.get(position);

        vh.restaurantName.setText(item.getName());
        vh.restaurantAddress.setText(item.getAddress());
        vh.restaurantMinOrder.append(String.format(Locale.getDefault(), "%.2f", item.getMinOrder()));

        Glide.with(context).load(item.getImage()).into(vh.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView restaurantImage;
        public TextView restaurantName, restaurantAddress, restaurantMinOrder;
        public CardView restaurantCv;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantImage = itemView.findViewById(R.id.restaurant_iv);
            restaurantName = itemView.findViewById(R.id.name_tv);
            restaurantAddress = itemView.findViewById(R.id.address_tv);
            restaurantMinOrder = itemView.findViewById(R.id.min_order_value_tv);
            restaurantCv = itemView.findViewById(R.id.restaurant_card_view);

            restaurantCv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.restaurant_card_view){
                Intent intent = new Intent(context, ShopActivity.class);
                intent.putExtra(ShopActivity.RESTAURANT_ID_KEY, data.get(getAdapterPosition()).getId());
                context.startActivity(intent);

                //TODO intent;
            }
        }
    }
}

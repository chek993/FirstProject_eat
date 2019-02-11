package com.example.firstproject_eat.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firstproject_eat.R;
import com.example.firstproject_eat.datamodels.Product;
import com.example.firstproject_eat.datamodels.Restaurant;
import com.example.firstproject_eat.ui.adapters.ProductAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.OnQuantityChangedListener {

    //UI components
    private TextView restaurantNameTv, restaurantAddressTv, totalTv, minOrderTv;
    private Button checkoutBtn;
    private ProgressBar progressBar;
    private ImageView restaurantIv;

    //RecyclerView components
    private RecyclerView productsRv;
    private LinearLayoutManager layoutManager;
    private ProductAdapter adapter;

    private Restaurant restaurant;

    private float total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        restaurantIv = findViewById(R.id.logo_iv);
        restaurantNameTv = findViewById(R.id.restaurant_name_tv);
        restaurantAddressTv = findViewById(R.id.restaurant_address_tv);

        totalTv = findViewById(R.id.total_value_tv);
        minOrderTv = findViewById(R.id.min_order_value_tv);

        progressBar = findViewById(R.id.progressBar);
        productsRv = findViewById(R.id.product_rv);
        checkoutBtn = findViewById(R.id.checkout_btn);

        binData();

        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this, restaurant.getProducts());

        adapter.setOnQuantityChangedListener(this);

        productsRv.setLayoutManager(layoutManager);
        productsRv.setAdapter(adapter);

        checkoutBtn.setOnClickListener(this);
    }

    //TODO hardcoded
    private void binData(){
        restaurant = getRestaurant();
        restaurant.setProducts(getProducts());

        restaurantNameTv.setText(restaurant.getName());
        restaurantAddressTv.setText(restaurant.getAddress());

        totalTv.setText(String.format(Locale.getDefault(), "%.2f", total));
        minOrderTv.setText(String.format(Locale.getDefault(), "%.2f", restaurant.getMinOrder()));

        Glide.with(this).load(restaurant.getImage()).into(restaurantIv);

        progressBar.setMax((int)(restaurant.getMinOrder() * 100));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.checkout_btn){
            Intent checkout = new Intent(this, CheckoutActivity.class);
            startActivity(checkout);
        }
    }

    //TODO hardcoded
    private Restaurant getRestaurant(){
        return new Restaurant("McDonald's", "address", 14.50f, "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png");
    }

    //TODO hardcoded
    private ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));
        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));
        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));
        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));
        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));

        return products;
    }

    private void updateTotal(float item){
        total += item;
        totalTv.setText(String.format(Locale.getDefault(), "%.2f", total));
    }

    private void updateProgress(int progress){
        progressBar.setProgress(progress);
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgress((int)(total * 100));
        checkoutBtn.setEnabled(total >= restaurant.getMinOrder());
    }
}

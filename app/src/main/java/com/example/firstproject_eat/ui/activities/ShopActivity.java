package com.example.firstproject_eat.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.firstproject_eat.R;
import com.example.firstproject_eat.datamodels.Product;
import com.example.firstproject_eat.datamodels.Restaurant;
import com.example.firstproject_eat.services.RestController;
import com.example.firstproject_eat.ui.adapters.ProductAdapter;
import com.example.firstproject_eat.ui.adapters.RestaurantAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.OnQuantityChangedListener, Response.Listener<String>, Response.ErrorListener {

    //UI components
    private TextView restaurantNameTv, restaurantAddressTv, totalTv, minOrderTv;
    private Button checkoutBtn;
    private ProgressBar progressBar, loadingProgressBar;
    private ImageView restaurantIv;

    //RecyclerView components
    private RecyclerView productsRv;
    private LinearLayoutManager layoutManager;
    private ProductAdapter adapter;

    private Menu menu;

    private Restaurant restaurant;

    private String idRestaurant;
    private RestController restController;

    private float total = 0;

    public static final String RESTAURANT_ID_KEY = "RESTAURANT_ID_KEY";
    private static final String TAG = ShopActivity.class.getSimpleName();
    private static final int LOGIN_REQUEST_CODE = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        loadingProgressBar = findViewById(R.id.progressBar_loading);

        restaurantIv = findViewById(R.id.logo_iv);
        restaurantNameTv = findViewById(R.id.restaurant_name_tv);
        restaurantAddressTv = findViewById(R.id.restaurant_address_tv);

        totalTv = findViewById(R.id.total_value_tv);
        minOrderTv = findViewById(R.id.min_order_value_tv);

        progressBar = findViewById(R.id.progressBar);
        productsRv = findViewById(R.id.product_rv);
        checkoutBtn = findViewById(R.id.checkout_btn);

        idRestaurant = getIntent().getStringExtra(ShopActivity.RESTAURANT_ID_KEY);

        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT.concat(idRestaurant), this, this);

        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this);

        adapter.setOnQuantityChangedListener(this);

        productsRv.setLayoutManager(layoutManager);
        productsRv.setAdapter(adapter);

        checkoutBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shop, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.login_menu){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,LOGIN_REQUEST_CODE);
        }else if(item.getItemId() == R.id.checkout_menu){
            startActivity(new Intent(this, CheckoutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void binData(){
        restaurantNameTv.setText(restaurant.getName());
        restaurantAddressTv.setText(restaurant.getAddress());

        totalTv.setText(String.format(Locale.getDefault(), "%.2f", total));
        minOrderTv.setText(String.format(Locale.getDefault(), "%.2f", restaurant.getMinOrder()));

        Glide.with(this).load(restaurant.getImage()).into(restaurantIv);

        progressBar.setMax((int)(restaurant.getMinOrder() * 100));
        adapter.setProducts(restaurant.getProducts());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.checkout_btn){
            Intent checkout = new Intent(this, CheckoutActivity.class);
            startActivity(checkout);
        }
    }

    /*TODO hardcoded
    private Restaurant getRestaurant(){
        return new Restaurant("McDonald's", "address", 14.50f, "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png");
    }*/

    /*TODO hardcoded
    private ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));
        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));
        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));
        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));
        products.add(new Product("Mc Menu", "good burger", "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png", 5F));

        return products;
    }*/

    private void updateTotal(float item){
        total += item;
        totalTv.setText(String.format(Locale.getDefault(), "%.2f", total));
    }

    private void updateProgress(int progress){
        progressBar.setProgress(progress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG,"requestCode " + requestCode);
        Log.d(TAG,"resultCode " + resultCode);

        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            // TODO login is successful manage result

            Log.d(TAG, data.getStringExtra("response"));

            menu.findItem(R.id.login_menu).setTitle(R.string.profile)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            startActivity(new Intent(ShopActivity.this,ProfileActivity.class));
                            return true;
                        }
                    });
        }
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgress((int)(total * 100));
        checkoutBtn.setEnabled(total >= restaurant.getMinOrder());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());
        loadingProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            restaurant = new Restaurant(jsonObject);
            binData();
            loadingProgressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

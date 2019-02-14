package com.example.firstproject_eat.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firstproject_eat.R;
import com.example.firstproject_eat.datamodels.Restaurant;
import com.example.firstproject_eat.services.RestController;
import com.example.firstproject_eat.ui.adapters.RestaurantAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    RecyclerView restaurantRV;
    RecyclerView.LayoutManager layoutManager;
    RestaurantAdapter adapter;
    ArrayList<Restaurant> restaurants = new ArrayList<>();
    ProgressBar loadingProgressBar;

    private RestController restController;

    SharedPreferences sharedPreferences;

    private static final String SharedPrefs = "com.example.firstproject_eat.general_pref";
    private static final String VIEW_MODE = "VIEW_MODE";

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantRV = findViewById(R.id.places_rv);
        loadingProgressBar = findViewById(R.id.progressBar_loading);

        layoutManager = getLayoutManager(getSavedLayoutManager());
        adapter = new RestaurantAdapter(this/*, getData()*/);

        adapter.setGridMode(getSavedLayoutManager());

        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);

        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT, this, this);

        /* Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://4a517745.ngrok.io/api/v1/restaurant";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, // HTTP request method
                url,    // Server link
                new Response.Listener<String>() {   // Listener for successful request
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        //Start parsing
                        try {
                            JSONArray restaurantJsonArray = new JSONArray(response);
                            for(int i = 0; i < restaurantJsonArray.length(); i++){
                                Restaurant restaurant = new Restaurant(restaurantJsonArray.getJSONObject(i));
                                restaurants.add(restaurant);
                            }
                            adapter.setData(restaurants);

                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {  // Listener for error request
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage()); // + " " + error.networkResponse.statusCode);
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        */

    }

    /*TODO hardcoded
    private ArrayList<Restaurant> getData(){
        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("McDonald's", "Via Tiburtina, 515, 00159 Roma RM", 8.50F, "https://static.seekingalpha.com/uploads/2018/7/24/saupload_3000px-McDonald_27s_SVG_logo.svg.png"));
        restaurants.add(new Restaurant("Burger King", "Via Tiburtina, 474, 00159 Roma RM", 7.30F, "https://upload.wikimedia.org/wikipedia/it/thumb/3/3a/Burger_King_Logo.svg/1013px-Burger_King_Logo.svg.png"));
        restaurants.add(new Restaurant("KFC", "Via Collatina, 858, 00132 Roma RM", 9.00F, "data:image/png;base64,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"));

        return restaurants;

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.view_mode).setIcon(getSavedLayoutManager() ? R.drawable.ic_action_name : R.drawable.ic_grid_mode);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.login_menu){
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }else if(item.getItemId() == R.id.checkout_menu) {
            startActivity(new Intent(this, CheckoutActivity.class));
            return true;
        }else if(item.getItemId() == R.id.view_mode){
            setLayoutManager();
            item.setIcon(adapter.isGridMode() ? R.drawable.ic_action_name : R.drawable.ic_grid_mode);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLayoutManager(){
        if (adapter.isGridMode()){
            layoutManager = new LinearLayoutManager(this);
        }else{
            layoutManager = new GridLayoutManager(this, 2);
        }

        adapter.setGridMode(!adapter.isGridMode());

        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);

        saveLayoutManager(adapter.isGridMode());
    }

    private RecyclerView.LayoutManager getLayoutManager(boolean isGridLayout){
        return isGridLayout ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this);
    }

    private void saveLayoutManager(boolean isGridLayout){
        sharedPreferences = getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(VIEW_MODE, isGridLayout);
        editor.apply();
    }

    private boolean getSavedLayoutManager(){
        sharedPreferences = getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        return sharedPreferences.getBoolean(VIEW_MODE, false);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0; i < jsonArray.length(); i++){
                restaurants.add(new Restaurant(jsonArray.getJSONObject(i)));
            }
            adapter.setData(restaurants);
            loadingProgressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

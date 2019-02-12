package com.example.firstproject_eat.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstproject_eat.R;
import com.example.firstproject_eat.datamodels.Order;
import com.example.firstproject_eat.datamodels.Product;
import com.example.firstproject_eat.datamodels.Restaurant;
import com.example.firstproject_eat.ui.adapters.OrderProductsAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView restaurantNameTv, restaurantAddressTv, totalTv, minOrderTv;
    private RecyclerView productRv;
    private Button payBtn;
    private LinearLayoutManager layoutManager;
    private OrderProductsAdapter adapter;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        restaurantNameTv = findViewById(R.id.restaurant_name_tv);
        restaurantAddressTv = findViewById(R.id.restaurant_address_tv);
        productRv = findViewById(R.id.product_rv);
        totalTv = findViewById(R.id.total_value_tv);
        minOrderTv = findViewById(R.id.min_order_value_tv);
        payBtn = findViewById(R.id.pay_btn);

        order = getOrder();

        bindData();

        layoutManager = new LinearLayoutManager(this);
        productRv.setLayoutManager(layoutManager);

        adapter = new OrderProductsAdapter(this, order.getProducts());
        productRv.setAdapter(adapter);
    }

    private void bindData(){
        restaurantNameTv.setText(order.getRestaurant().getName());
        restaurantAddressTv.setText(order.getRestaurant().getAddress());
        totalTv.setText(String.format(Locale.getDefault(), "%.2f", order.getTotal()));
        minOrderTv.setText(String.format(Locale.getDefault(), "%.2f", order.getRestaurant().getMinOrder()));
    }

    //TODO hardcoded
    private Order getOrder(){
        Order mockOrder = new Order();

        mockOrder.setProducts(getProducts());
        mockOrder.setRestaurant(getRestaurant());
        mockOrder.setTotal(allSubtotal(mockOrder));

        return mockOrder;
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

        setInstantQuantity(products);

        return products;
    }

    //TODO hardcoded
    private void setInstantQuantity(ArrayList<Product> products){
        for(Product p : products){
            p.setQuantity(2);
        }
    }

    //TODO hardcoded
    private float allSubtotal(Order order){
        float subtotal = 0F;

        for(Product p : order.getProducts()){
            subtotal += p.getSubtotal();
        }

        return subtotal;
    }

    @Override
    public void onClick(View v) {
        //TODO manage click
    }
}

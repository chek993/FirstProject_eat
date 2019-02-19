package com.example.firstproject_eat.ui.adapters;

import android.content.Context;
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
import com.example.firstproject_eat.datamodels.Product;

import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private ArrayList<Product> products;
    private Context context;

    private OnQuantityChangedListener onQuantityChangedListener;

    public ProductAdapter(Context context, ArrayList<Product> products){
        inflater = LayoutInflater.from(context);
        this.products = products;
        this.context = context;
    }

    public ProductAdapter(Context context){
        inflater = LayoutInflater.from(context);
        products = new ArrayList<>();
        this.context = context;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public interface OnQuantityChangedListener{
        void onChange(float price);
    }

    public OnQuantityChangedListener getOnQuantityChangedListener() {
        return onQuantityChangedListener;
    }

    public void setOnQuantityChangedListener(OnQuantityChangedListener onQuantityChangedListener) {
        this.onQuantityChangedListener = onQuantityChangedListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_products, viewGroup, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Product product = products.get(i);
        ProductViewHolder vh = (ProductViewHolder) viewHolder;

        Glide.with(context).load(product.getImage()).into(vh.productImage);
        vh.productName.setText(product.getName());
        vh.productDescription.setText(product.getDescription());
        vh.productPrice.setText(String.format(Locale.getDefault(), "%.2f", product.getPrice()));
        vh.productQuantity.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView productImage;
        public TextView productName, productDescription, productPrice, productQuantity;
        public Button minusQuantityBtn, plusQuantityBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_iv);
            productName = itemView.findViewById(R.id.product_name_tv);
            productDescription = itemView.findViewById(R.id.product_description_tv);
            productPrice = itemView.findViewById(R.id.product_price_tv);
            productQuantity = itemView.findViewById(R.id.product_quantity_tv);
            minusQuantityBtn = itemView.findViewById(R.id.minus_btn);
            plusQuantityBtn = itemView.findViewById(R.id.plus_btn);

            minusQuantityBtn.setOnClickListener(this);
            plusQuantityBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //TODO switch plus and minus
            Product product = products.get(getAdapterPosition());

            if(v.getId() == R.id.plus_btn){
                product.increaseQuantity();
                onQuantityChangedListener.onChange(product.getPrice());

            }else if(v.getId() == R.id.minus_btn){
                if(product.getQuantity() <= 0){
                    return;
                }
                product.decreaseQuantity();
                onQuantityChangedListener.onChange(-1 * product.getPrice());
            }

            notifyItemChanged(getAdapterPosition());
        }
    }
}

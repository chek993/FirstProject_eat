package com.example.firstproject_eat.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.firstproject_eat.R;
import com.example.firstproject_eat.datamodels.Product;

import java.util.ArrayList;
import java.util.Locale;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.OrderProductViewHolder> {

    private ArrayList<Product> dataSet;
    private Context context;
    private LayoutInflater inflater;

    public OrderProductsAdapter(Context context, ArrayList<Product> dataSet){
        this.dataSet = dataSet;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new OrderProductViewHolder(inflater.inflate(R.layout.item_order_product, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder orderProductViewHolder, int i) {
        Product product = dataSet.get(i);

        orderProductViewHolder.productNameTv.setText(product.getName());
        orderProductViewHolder.quantityTv.setText(String.valueOf(product.getQuantity()));
        orderProductViewHolder.subtotalTv.setText(String.format(Locale.getDefault(), "%.2f", product.getSubtotal()));
    }

    @Override
    public int getItemCount() {

        return dataSet.size();
    }

    public class OrderProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView quantityTv, productNameTv, subtotalTv;
        private ImageButton removeBtn;

        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);

            quantityTv = itemView.findViewById(R.id.quantity_tv);
            productNameTv = itemView.findViewById(R.id.product_name_tv);
            subtotalTv = itemView.findViewById(R.id.subtotal_tv);
            removeBtn = itemView.findViewById(R.id.remove_btn);

            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //TODO alertDialog
            dataSet.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
        }
    }
}

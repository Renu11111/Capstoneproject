package com.app.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.R;
import com.app.myapplication.model.Product;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    Context context;
    List<Product> products;
    private View.OnClickListener clickListener;

    public ProductListAdapter(Context context, List<Product> products, View.OnClickListener clickListener) {
        this.context = context;
        this.products = products;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ProductListAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product, parent, false);
        return new ProductListAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.txt1.setText(product.name);
        holder.txt2.setText("$" + product.sale_price);
        holder.btn.setTag(product);
        Glide.with(context).load(product.image_url).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txt1, txt2;
        Button btn;
        ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txt_product_name);
            txt2 = itemView.findViewById(R.id.txt_product_name);
            btn = itemView.findViewById(R.id.btn_details);
            imageView = itemView.findViewById(R.id.image_product);
            btn.setOnClickListener(clickListener);
        }
    }
}
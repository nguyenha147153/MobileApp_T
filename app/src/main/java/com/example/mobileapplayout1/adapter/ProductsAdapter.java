package com.example.mobileapplayout1.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapplayout1.DetailActivity;
import com.example.mobileapplayout1.R;
import com.example.mobileapplayout1.model.Products;

import java.text.DecimalFormat;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>{

    private List<Products> mlistProduct;
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    Context context;

    public ProductsAdapter(Context context,List<Products> mProduct) {
        this.context = context;
        this.mlistProduct = mProduct;
    }
    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ProductsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Products product = mlistProduct.get(position);

        if (product == null) {
            return;
        }
        else {
            Glide.with(holder.ImagePhone.getContext()).load(product.getUrlImg()).into(holder.ImagePhone);
            holder.Name.setText(product.getProductName());
            holder.Price.setText(formatPrice.format(product.getProductPrice()) + " VNĐ");
            holder.Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDetailGotoProduct(product);
                }
            });
        }
    }

    private void onDetailGotoProduct(Products product) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_item",product);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mlistProduct != null){
            return mlistProduct.size();
        }else
            return 0;
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private CardView Layout;
        private TextView Name;
        private TextView Price;
        private ImageView ImagePhone;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            Layout = itemView.findViewById(R.id.item_pd_layout);
            Price = itemView.findViewById(R.id.item_pd_price);
            Name = itemView.findViewById(R.id.item_pd_name);
            ImagePhone = itemView.findViewById(R.id.item_pd_phone_image);

        }
    }
}

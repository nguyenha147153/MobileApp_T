package com.example.mobileapplayout1.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapplayout1.DetailActivity;
import com.example.mobileapplayout1.DetailHotActivity;
import com.example.mobileapplayout1.R;
import com.example.mobileapplayout1.model.HotSalePhone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HotSalePhoneAdapter extends RecyclerView.Adapter<HotSalePhoneAdapter.HotSalePhoneViewHolder> {

    Context context;
    ArrayList<HotSalePhone> list;
    List<HotSalePhone> hotSalePhoneList;
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");

    public HotSalePhoneAdapter(Context context, List<HotSalePhone> HotSalePhoneList) {
        this.context = context;
        this.hotSalePhoneList = HotSalePhoneList;
    }

    @NonNull
    @Override
    public HotSalePhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hot_sale_phone_row_item, parent, false);
        return new HotSalePhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotSalePhoneViewHolder holder, int position) {
        final HotSalePhone phone = hotSalePhoneList.get(position);

        if(phone==null){

        }
        else {
            Glide.with(holder.phoneImage.getContext()).load(phone.getUrlImg()).into(holder.phoneImage);
            holder.name.setText(phone.getProductName());
            holder.price.setText(formatPrice.format(phone.getProductPrice()) + "VNĐ");
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickDetail(phone);
                }
            });
        }

    }

    private void onClickDetail(HotSalePhone phone) {
        Intent intent = new Intent(context, DetailHotActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_item",phone);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        int limit = 3;
        return Math.min(hotSalePhoneList.size(), limit);
    }


    public static final class HotSalePhoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView layout;
        ImageView phoneImage;
        TextView price, name;


        public HotSalePhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item);
            phoneImage = itemView.findViewById(R.id.phone_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name_phone);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
//        this.iClickItemProductListener.onClickItemProduct(hotSalePhoneList.get(getAdapterPosition()));
        }
    }
}

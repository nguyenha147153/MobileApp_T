package com.example.mobileapplayout1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapplayout1.R;
import com.example.mobileapplayout1.model.DetailOrder;
import com.example.mobileapplayout1.model.Order;
import com.google.firebase.database.snapshot.Index;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HistoryProducttAdapter extends RecyclerView.Adapter<HistoryProducttAdapter.HotSalePhoneViewHolder> {

    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    private ArrayList<DetailOrder> listDetailOrder;
    private ArrayList<Order> listOrder;
    private Context context;


    public HistoryProducttAdapter(ArrayList<DetailOrder> listDetailOrder, Context context, ArrayList<Order> listOrder) {

        this.listDetailOrder = listDetailOrder;
        this.context = context;
        this.listOrder = listOrder;

    }

    @NonNull
    @Override
    public HotSalePhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HotSalePhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotSalePhoneViewHolder holder, int position) {
        DetailOrder detailOrder = listDetailOrder.get(position);
        if (detailOrder == null){
            return;
        }
        else {
            Glide.with(holder.imgHitoryProduct.getContext()).load(detailOrder.getUrlImg()).into(holder.imgHitoryProduct);
            holder.tvHitoryProductName.setText(detailOrder.getProductName());
            holder.tvHitoryProductNum.setText(String.valueOf(detailOrder.getNumProduct()));
            holder.tvHitoryProductPrice.setText(formatPrice.format(detailOrder.getProductPrice()) + "VNĐ");
            holder.tvHitoryProductStatus.setText(detailOrder.getStatus());
            holder.tvHitoryProductOrderNo.setText(detailOrder.getOrderNo().toUpperCase());

            for (Order order : listOrder){
                if (order.getOrderNo().equals(detailOrder.getOrderNo() ) ){
                    holder.tvHitoryProductDate.setText(order.getDateOrder());
                    break;
                }
            }
        }
    }



    @Override
    public int getItemCount() {
        if (listDetailOrder.size() != 0){
            return listDetailOrder.size();
        }else {
            return 0;
        }
    }


    public class HotSalePhoneViewHolder extends RecyclerView.ViewHolder {


        ImageView imgHitoryProduct;
        TextView tvHitoryProductName,tvHitoryProductNum,tvHitoryProductPrice,tvHitoryProductDate
                ,tvHitoryProductStatus,tvHitoryProductOrderNo;

        public HotSalePhoneViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHitoryProduct = itemView.findViewById(R.id.img_hitory_product);
            tvHitoryProductName = itemView.findViewById(R.id.tv_hitory_product_name);
            tvHitoryProductNum = itemView.findViewById(R.id.tv_hitory_product_num);
            tvHitoryProductPrice = itemView.findViewById(R.id.tv_hitory_product_price);
            tvHitoryProductDate = itemView.findViewById(R.id.tv_hitory_product_date);
            tvHitoryProductStatus = itemView.findViewById(R.id.tv_hitory_product_status);
            tvHitoryProductOrderNo = itemView.findViewById(R.id.tv_hitory_product_orderNo);
        }
    }

}

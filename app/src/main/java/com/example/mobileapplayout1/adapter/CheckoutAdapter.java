package com.example.mobileapplayout1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapplayout1.R;
import com.example.mobileapplayout1.helper.ManagementCart;
import com.example.mobileapplayout1.model.HotSalePhone;
import com.example.mobileapplayout1.my_interface.ChangeNumberItemsListener;
import com.example.mobileapplayout1.my_interface.IClickItemProductListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.HotSalePhoneViewHolder> {

    private ChangeNumberItemsListener changeNumberItemsListener;
    private ManagementCart managementCart;
    private ArrayList<HotSalePhone> hotSalePhoneList;
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");

    public CheckoutAdapter(ArrayList<HotSalePhone> hotSalePhoneList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {

        this.hotSalePhoneList = hotSalePhoneList;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;

    }

    @NonNull
    @Override
    public HotSalePhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_checkout, parent, false);
        return new HotSalePhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotSalePhoneViewHolder holder, int position) {
        holder.name.setText(hotSalePhoneList.get(position).getProductName());
//        holder.price.setText(formatPrice.format(phone.getProductPrice()));
//        holder.priceEachIteam.setText(formatPrice.format(hotSalePhoneList.get(position).getProductPrice())+ " VNĐ");
        holder.totalEachIteam.setText(formatPrice.format(Math.round(hotSalePhoneList.get(position).getNumProduct()*hotSalePhoneList.get(position).getProductPrice()))+" VNĐ");
        holder.num.setText(String.valueOf(hotSalePhoneList.get(position).getNumProduct()));
//        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(hotSalePhoneList.get(position).getUrlImg(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(hotSalePhoneList.get(position).getUrlImg()).into(holder.phoneImage);

        }






    @Override
    public int getItemCount() {
            return hotSalePhoneList.size();
    }


    public class HotSalePhoneViewHolder extends RecyclerView.ViewHolder {


        ImageView phoneImage;
        TextView priceEachIteam, name, totalEachIteam,num;
        IClickItemProductListener iClickItemProductListener;

        public HotSalePhoneViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneImage = itemView.findViewById(R.id.checkout_picCard);
//            priceEachIteam = itemView.findViewById(R.id.checkout_feeEachItem);
            name = itemView.findViewById(R.id.checkout_namePrd);
            totalEachIteam = itemView.findViewById(R.id.checkout_totalEachItem);
            num = itemView.findViewById(R.id.checkout_numItem);
        }
    }

}

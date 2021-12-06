package com.example.mobileapplayout1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobileapplayout1.helper.ManagementCart;
import com.example.mobileapplayout1.model.HotSalePhone;

import java.text.DecimalFormat;

public class DetailHotActivity extends AppCompatActivity {
    TextView name, price, description;
    Button buy;
    ImageView imgProduct, back;
    private ManagementCart managementCart;
    int numberInCart = 1;
    private DecimalFormat format = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hot);
        Bundle bundle = getIntent().getExtras();
        if(bundle==null){
            return;
        }
        matching();
        HotSalePhone phone = (HotSalePhone) bundle.get("object_item");
        managementCart = new ManagementCart(this);
        name.setText(phone.getProductName());
        price.setText(format.format(phone.getProductPrice())+" VNĐ");
        description.setText(phone.getDescription());
        Glide.with(imgProduct.getContext()).load(phone.getUrlImg()).into(imgProduct);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone.setNumProduct(numberInCart);
                managementCart.insertProduct(phone);
            }
        });
        //imgProduct.setImageURI(Uri.parse(phone.getUrlImg()));
    }

    private void matching() {
        name = (TextView) findViewById(R.id.detailhot_tv_name);
        price= (TextView) findViewById(R.id.detailhot_tv_price);
        description= (TextView) findViewById(R.id.detailhot_tv_description);
        buy = (Button) findViewById(R.id.detail_btn_buy);
        imgProduct = (ImageView) findViewById(R.id.detailhot_img_product);
        back = (ImageView) findViewById(R.id.detailhot_img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapplayout1.adapter.CartListAdapter;
import com.example.mobileapplayout1.adapter.CheckoutAdapter;
import com.example.mobileapplayout1.helper.ManagementCart;
import com.example.mobileapplayout1.model.DetailOrder;
import com.example.mobileapplayout1.model.HotSalePhone;
import com.example.mobileapplayout1.model.Products;
import com.example.mobileapplayout1.my_interface.ChangeNumberItemsListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private Context mContext;
    private TextView  totalTxt, order;
    private EditText CartCustName, CartCustAddress, CartCustPhone;
    private double tax;
    private List<Products> listProduct;
    private List<HotSalePhone> listhotSalePhones;
    private ScrollView scrollView;
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        managementCart = new ManagementCart(this);
        matching();
        initList();
        bottomNavigation();
        calculateCart();
    }

    private void bottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.mnu_index);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnu_profile:
                        startActivity(new Intent(CheckoutActivity.this, Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_products:
                        startActivity(new Intent(CheckoutActivity.this, ProductsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_index:
                        Toast.makeText(CheckoutActivity.this,"Index Here",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.mnu_setting:
                        startActivity(new Intent(CheckoutActivity.this, LogoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_giohang);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, CartListActivity.class));
            }
        });
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter= new CheckoutAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCart();
            }
        });
        recyclerViewList.setAdapter(adapter);
        scrollView.setVisibility(View.VISIBLE);

    }
    public void calculateCart(){

        totalTxt.setText(formatPrice.format(managementCart.getTotalItem())+ " VNĐ");
    }

    private void matching() {
        recyclerViewList = findViewById(R.id.checkout_recyclerview);
        totalTxt = findViewById(R.id.checkout_tv_total);
        scrollView = findViewById(R.id.checkout_scrollView);
        CartCustName = findViewById(R.id.et_cart_cust_name);
        CartCustAddress = findViewById(R.id.et_cart_cust_address);
        CartCustPhone = findViewById(R.id.et_cart_cust_phone);
        order = findViewById(R.id.tv_order);
        order.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                // Thêm dữ liệu các thông tin đã order
                addaaDataOrder();
            }
        });
    }
    private void addaaDataOrder() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DbOrder");
        Map<String,Object> map = new HashMap<>();
        double  total = managementCart.getTotalItem();
        // Lấy ngày order = now
        Date date = new Date(System.currentTimeMillis());
        map.put("dateOrder", date.toString());
        map.put("custName",CartCustName.getText().toString());
        map.put("custAddress",CartCustAddress.getText().toString());
        map.put("custPhone",CartCustPhone.getText().toString());
        listhotSalePhones = managementCart.getListCart();
        int num = 0;
        for (HotSalePhone hotSalePhone: listhotSalePhones ){
            num = num + hotSalePhone.getNumProduct();
        }
        map.put("numProduct",num);
        map.put("totalPrice",total);
        map.put("status","Đang chờ xác nhận");
        map.put("idCus",1);
        map.put("idOrd",1);

        //Add thông tin order
        String odrKey = myRef.push().getKey();
        myRef.child(odrKey).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                List<DetailOrder> listDetailOrder = makeDetailOrder(odrKey);
                 //Add thông tin detail order
                for (DetailOrder detailOrder : listDetailOrder){
                    myRef.child(odrKey).child("detail").child(myRef.push().getKey())
                            .setValue(detailOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CheckoutActivity.this, "Đã đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            listhotSalePhones.clear();
                            startActivity(new Intent(CheckoutActivity.this, index.class));
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext,"Đăng ký đơn hàng thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<DetailOrder> makeDetailOrder(String odrNo) {
        List<DetailOrder> listDetailOrder = new ArrayList<>();
        for (HotSalePhone hotSalePhone : listhotSalePhones){
            DetailOrder detailOrder = new DetailOrder();
            detailOrder.setOrderNo(odrNo);
            detailOrder.setProductName(hotSalePhone.getProductName());
            detailOrder.setProductPrice(hotSalePhone.getProductPrice());
            detailOrder.setUrlImg(hotSalePhone.getUrlImg());
            detailOrder.setNumProduct(hotSalePhone.getNumProduct());
            detailOrder.setStatus("Đang chờ xác nhận");
            listDetailOrder.add(detailOrder);
        }
        return listDetailOrder;
    }
}
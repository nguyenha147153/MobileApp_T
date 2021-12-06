package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapplayout1.adapter.CartListAdapter;
import com.example.mobileapplayout1.helper.ManagementCart;
import com.example.mobileapplayout1.my_interface.ChangeNumberItemsListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class CartListActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private RelativeLayout rlCartEmpty;
    private TextView totalTxt;
    private ScrollView scrollView;
    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);

        matching();
        initList();
        bottomNavigation();
        calculateCart();
    }
    private void bottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnu_profile:
                        startActivity(new Intent(CartListActivity.this, Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_products:
                        startActivity(new Intent(CartListActivity.this, ProductsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_index:
                        startActivity(new Intent(CartListActivity.this, index.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_history:
                        startActivity(new Intent(CartListActivity.this, HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_setting:
                        startActivity(new Intent(CartListActivity.this, logout.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


//        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        TextView checkout = findViewById(R.id.tv_checkout);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CheckoutActivity.class));
            }
        });
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter= new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCart();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if(managementCart.getListCart().isEmpty()){
            rlCartEmpty.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else {
            rlCartEmpty.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
    public void calculateCart(){
//        double total = managementCart.getTotalItem();

        totalTxt.setText(formatPrice.format(managementCart.getTotalItem())+ " VNĐ");
    }
    private void matching() {
        recyclerViewList = findViewById(R.id.cart_recyclerview);
        totalTxt = findViewById(R.id.cart_tv_total);
        scrollView = findViewById(R.id.cart_scrollView);
        rlCartEmpty = findViewById(R.id.rl_cart_empty);
    }
}
package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapplayout1.adapter.HistoryProducttAdapter;
import com.example.mobileapplayout1.model.DetailOrder;
import com.example.mobileapplayout1.model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private ArrayList<Order> listOrder;
    private ArrayList<DetailOrder> listDetailOrder;
    private EditText edtHistoryPhone;
    private Button btnHistorySearch;
    private RecyclerView rcvHitorySearch;
    private Context context;
    private HistoryProducttAdapter historyProducttAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        matching();
        if (!edtHistoryPhone.getText().toString().trim().isEmpty()){
            findOrder();
        }
        bottomNavigation();
    }

    private void bottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.mnu_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnu_profile:
                        startActivity(new Intent(HistoryActivity.this, Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_products:
                        startActivity(new Intent(HistoryActivity.this, ProductsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_index:
                        startActivity(new Intent(HistoryActivity.this, index.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_history:
                        Toast.makeText(HistoryActivity.this,"History Here",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.mnu_setting:
                        startActivity(new Intent(HistoryActivity.this, logout.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }

    private void matching() {
        listOrder = new ArrayList<>();
        listDetailOrder = new ArrayList<>();

        edtHistoryPhone = findViewById(R.id.edt_history_phone);

        rcvHitorySearch = findViewById(R.id.rcv_hitory_search);
        btnHistorySearch = findViewById(R.id.btn_history_search);
        btnHistorySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findOrder();
            }
        });
    }

    private void findOrder() {
        // Clear các list dữ liệu khi tìm kiếm
        listOrder.clear();
        listDetailOrder.clear();

        // Kết nối tới data base
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DbOrder");

        // Lấy thông tin order
        myRef.orderByChild("custPhone").equalTo(edtHistoryPhone.getText().toString())
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataOrder : snapshot.getChildren()){
                            Order order = dataOrder.getValue(Order.class);
                            order.setOrderNo(dataOrder.getKey());
                            listOrder.add(order);
                        }

                        if (listOrder.size() > 0){
                            // Lấy thông tin detail order
                            findDetailOrder(myRef);
                        }
                        else {
                            Toast.makeText(HistoryActivity.this,"Không tìm thấy lịch sử đặt hàng",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HistoryActivity.this,"Không lấy được thông tin đơn hàng từ firebase",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void findDetailOrder(DatabaseReference myRef) {
        if (listOrder.size() > 0){
            for (int i = 0; i<listOrder.size(); i++){
                Order order = listOrder.get(i);
                myRef.child(order.getOrderNo()).child("detail").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataDetail : snapshot.getChildren()){
                            DetailOrder detailOrder = dataDetail.getValue(DetailOrder.class);
                            listDetailOrder.add(detailOrder);
                        }

                        // set data HistoryProductAdapter
                        if (listDetailOrder.size() > 0){
                            setDataHistoryProductAdapter();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HistoryActivity.this,"Không lấy được chi tiết đơn hàng từ firebase",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private void setDataHistoryProductAdapter() {
        historyProducttAdapter = new HistoryProducttAdapter(listDetailOrder,this,listOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvHitorySearch.setLayoutManager(linearLayoutManager);
        rcvHitorySearch.setAdapter(historyProducttAdapter);
    }

}
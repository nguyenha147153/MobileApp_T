package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mobileapplayout1.adapter.HotSalePhoneAdapter;
import com.example.mobileapplayout1.model.HotSalePhone;
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
import java.util.Locale;

public class index extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView hotSalePhoneRecycler;
    HotSalePhoneAdapter hotSalePhoneAdapter;
    SearchView search;
    private List<HotSalePhone> listAllPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        matching();
        listAllPhone = getDataProduct();
        setHotSalePhoneRecycler(listAllPhone);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.mnu_index);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_giohang);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(index.this, CartListActivity.class));
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnu_profile:
                        startActivity(new Intent(index.this, Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_products:
                        startActivity(new Intent(index.this, ProductsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_index:
                        Toast.makeText(index.this,"Index Here",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.mnu_history:
                        startActivity(new Intent(index.this, HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_setting:
                        startActivity(new Intent(index.this, logout.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private  void setHotSalePhoneRecycler(List<HotSalePhone> hotSalePhoneList){
        hotSalePhoneRecycler= findViewById(R.id.hotsale_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        hotSalePhoneRecycler.setLayoutManager(layoutManager);
        hotSalePhoneAdapter = new HotSalePhoneAdapter(this, hotSalePhoneList);

        hotSalePhoneRecycler.setAdapter(hotSalePhoneAdapter);


    }
    public List<HotSalePhone> getDataProduct(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("DbProduct");
        List<HotSalePhone> list = new ArrayList<>();
        if(reference != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    hotSalePhoneAdapter.notifyDataSetChanged();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        HotSalePhone phone = data.getValue(HotSalePhone.class);
                        phone.setId(data.getKey());
                        list.add(phone);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("FIREBASE", "load:onCancelled", error.toException());
                }
            });
        }
        if(search != null){
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
        return list;
    }

    private void search(String newText) {
        ArrayList<HotSalePhone> mList = new ArrayList<>();
        for(HotSalePhone hsp : listAllPhone){
            if(hsp.getProductName().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                mList.add(hsp);
            }
            HotSalePhoneAdapter adapter = new HotSalePhoneAdapter(this,mList);
            hotSalePhoneRecycler.setAdapter(adapter);
        }
    }

    private void matching(){
        search = (SearchView) findViewById(R.id.pd_sv_search);
    }

}
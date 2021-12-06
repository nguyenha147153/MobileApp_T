package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mobileapplayout1.adapter.HotSalePhoneAdapter;
import com.example.mobileapplayout1.adapter.ProductsAdapter;
import com.example.mobileapplayout1.model.HotSalePhone;
import com.example.mobileapplayout1.model.Products;
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

public class ProductsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private List<Products> listAllProduct;
    private RecyclerView rcvProduct;
    private ProductsAdapter productAdapter;
    SearchView search;
    private Products products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        
        matching();
        getListProducts();

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_giohang);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductsActivity.this, CartListActivity.class));
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.mnu_products);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnu_profile:
                        startActivity(new Intent(ProductsActivity.this, Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_products:
                        Toast.makeText(ProductsActivity.this,"Product Here",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.mnu_index:
                        startActivity(new Intent(ProductsActivity.this, index.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_history:
                        startActivity(new Intent(ProductsActivity.this, HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_setting:
                        startActivity(new Intent(ProductsActivity.this, logout.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private List<Products> getListProducts() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("DbProduct");
        if (reference != null){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Products products1 = dataSnapshot.getValue(Products.class);
                        listAllProduct.add(products1);
                    }
                    productAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProductsActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_LONG).show();
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
        return listAllProduct;
    }

    private void search(String newText) {
        ArrayList<Products> products = new ArrayList<>();
        for(Products prd : listAllProduct){
            if(prd.getProductName().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                products.add(prd);
            }
            ProductsAdapter adapter = new ProductsAdapter(this,products);
            rcvProduct.setAdapter(adapter);
        }
    }


    private void matching() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        rcvProduct = findViewById(R.id.ryc_products);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(dividerItemDecoration);

        listAllProduct = new ArrayList<>();
        productAdapter = new ProductsAdapter(this,listAllProduct);

        rcvProduct.setAdapter(productAdapter);
        
        search = (SearchView) findViewById(R.id.pd_sv_search);
    }
}
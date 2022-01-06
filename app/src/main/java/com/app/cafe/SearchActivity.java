package com.app.cafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText TF_location;
    Button B_search;
    ImageView backTimKiem;
    ArrayList<Food> arrayListfood= new ArrayList<>();
    DatabaseFood databaseFood;
    RecyclerView rcvsearch;
    FavoriteAdapter favoriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TF_location = findViewById(R.id.TF_location);
        backTimKiem = findViewById(R.id.backTimKiem);
        B_search = findViewById(R.id.B_search);
        rcvsearch = findViewById(R.id.rcvsearch);
        arrayListfood = new ArrayList<>();

        databaseFood = new DatabaseFood(SearchActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvsearch.setHasFixedSize(true);
        rcvsearch.setLayoutManager(layoutManager);

//        daoFood.getAll(new Foodcallback() {
//            @Override
//            public void onSuccess(ArrayList<Food> lists) {
//                arrayListfood.addAll(lists);
//                favoriteAdapter = new FavoriteAdapter(arrayListfood,SearchActivity.this);
//                rcvsearch.setAdapter(favoriteAdapter);
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
        if (TF_location.getText().toString().equals("")){
            arrayListfood.clear();
            favoriteAdapter = new FavoriteAdapter(arrayListfood,SearchActivity.this);
            rcvsearch.setAdapter(favoriteAdapter);
        }
        B_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseFood.getAll(new FoodCallBack() {
                    @Override
                    public void onSuccess(ArrayList<Food> lists) {
                        arrayListfood.clear();
                        for (int i =0;i<lists.size();i++){
                            if (lists.get(i).getNamefood().toLowerCase().contains(TF_location.getText().toString())){
                                arrayListfood.add(lists.get(i));
                                favoriteAdapter = new FavoriteAdapter(arrayListfood,SearchActivity.this);
                                rcvsearch.setAdapter(favoriteAdapter);
                            }

                        }

                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });

        backTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBack = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iBack);
                finish();
            }
        });
    }
}
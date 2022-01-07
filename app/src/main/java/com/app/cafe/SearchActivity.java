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

import com.app.cafe.Adapter.YeuthichAdapter;
import com.app.cafe.CallBack.CafeCallBack;
import com.app.cafe.Databases.DatabaseCafe;
import com.app.cafe.Model.Cafe;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText TF_location;
    Button B_search;
    ImageView backTimKiem;
    ArrayList<Cafe> arrayListcafe= new ArrayList<>();

    DatabaseCafe databaseCafe;
    RecyclerView rcvsearch;
    YeuthichAdapter yeuthichAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TF_location = findViewById(R.id.TF_location);
        backTimKiem = findViewById(R.id.backTimKiem);
        B_search = findViewById(R.id.B_search);
        rcvsearch = findViewById(R.id.rcvsearch);
        arrayListcafe = new ArrayList<>();


        databaseCafe = new DatabaseCafe(SearchActivity.this);
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
            arrayListcafe.clear();
            yeuthichAdapter = new YeuthichAdapter(arrayListcafe,SearchActivity.this);
            rcvsearch.setAdapter(yeuthichAdapter);
        }
        B_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseCafe.getAll((new CafeCallBack() {
                    @Override
                    public void onSuccess(ArrayList<Cafe> lists) {
                        arrayListcafe.clear();
                        for (int i =0;i<lists.size();i++){
                            if (lists.get(i).getTenquan().toLowerCase().contains(TF_location.getText().toString())){
                                arrayListcafe.add(lists.get(i));
                                yeuthichAdapter = new YeuthichAdapter(arrayListcafe,SearchActivity.this);
                                rcvsearch.setAdapter(yeuthichAdapter);
                            }

                        }
                    }

                    @Override
                    public void onError(String message) {

                    }
                }));
            }
        });


        backTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBack = new Intent(getApplicationContext(), trangchu.class);
                startActivity(iBack);
                finish();
            }
        });
    }
}
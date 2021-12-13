package com.app.cafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.cafe.view.DangNhapActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton imgHinh;
    RelativeLayout manhinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgHinh = (ImageButton) findViewById(R.id.imageViewHinh);
        manhinh = (RelativeLayout) findViewById(R.id.manhinh);
     //   manhinh.setBackgroundResource(R.drawable.background);
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itdangnhap = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(itdangnhap);
                finish();
            }
        });
    }
}
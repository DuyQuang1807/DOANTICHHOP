package com.app.cafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.cafe.CallBack.DatChoCallback;
import com.app.cafe.CallBack.UserCallBack;
import com.app.cafe.Databases.DatabaseDatCho;
import com.app.cafe.Databases.DatabaseUser;
import com.app.cafe.Model.DatCho;
import com.app.cafe.Model.User;
import com.app.cafe.view.DangNhapActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class DatChoActivity extends AppCompatActivity {
    Button btnDatCho;
    ImageView btnback;
    EditText edtslbanDC,edtSoDienThoaiDC,edtHotenDC,edtgioDC,edtngaydat;

    String  phone, hoten;

    ArrayList<DatCho> datChoArrayList = new ArrayList<>();
    DatabaseUser databaseUser;
    DatabaseDatCho databaseDatCho;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    ImageView back;



    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datcho);

        btnback = findViewById(R.id.btnback);
        btnDatCho = findViewById(R.id.btnDatCho);
        edtslbanDC = findViewById(R.id.edtSlbanDC);
        edtHotenDC = findViewById(R.id.edtHotenDC);
        edtSoDienThoaiDC = findViewById(R.id.edtSoDienThoaiDC);
        edtngaydat = findViewById(R.id.edtngaydat);
        edtgioDC = findViewById(R.id.edtgioDC);
        mAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseUser = new DatabaseUser(getApplicationContext());
        databaseUser.getAll(new UserCallBack() {
            @Override
            public void onSuccess(ArrayList<User> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getToken() != null && lists.get(i).getToken().equalsIgnoreCase(firebaseUser.getUid())) {
                        hoten = lists.get(i).getName();
                        phone = lists.get(i).getPhone();

                    }
                }

                edtHotenDC.setText(hoten);
                edtSoDienThoaiDC.setText(phone);
            }

            @Override
            public void onError(String message) {

            }
        });


        btnDatCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("DatCho");
                    final String HoTen = edtHotenDC.getText().toString();
                    final String SDT = edtSoDienThoaiDC.getText().toString();
                    final String SLDat = edtslbanDC.getText().toString();
                    final String NgayDat = edtngaydat.getText().toString();
                    final String GioDat = edtgioDC.getText().toString();

                    myRef.child(HoTen).child("phone").setValue(SDT);
                    myRef.child(HoTen).child("SLDat").setValue(SLDat);
                    myRef.child(HoTen).child("NgayDat").setValue(NgayDat);
                    myRef.child(HoTen).child("GioDat").setValue(GioDat);

                    Toast.makeText(getApplicationContext(), "Thành Công.", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(getApplicationContext(),trangchu.class);
                    startActivity(a);
                    finish();
                }
                catch (Exception ex)
                {
                }


                //  DatabaseDatCho.insert(user);

            }
        });





        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), trangchu.class));
                finish();
            }
        });
    }




}
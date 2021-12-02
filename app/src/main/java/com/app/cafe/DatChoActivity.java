package com.app.cafe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class DatChoActivity extends AppCompatActivity {
    Button btnDatCho, btnTest;
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
        btnTest = findViewById(R.id.tt);
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

        databaseDatCho= new DatabaseDatCho(getApplicationContext());
        databaseDatCho.getAll(new DatChoCallback() {
            @Override
            public void onSuccess(ArrayList<DatCho> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getToken() != null && lists.get(i).getToken().equalsIgnoreCase(firebaseUser.getUid())) {
                        hoten = lists.get(i).getHoTen();
                        phone = lists.get(i).getSoDienThoai();


                    }
                }


            }

            @Override
            public void onError(String message) {

            }
        });



        btnDatCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String HoTen = edtHotenDC.getText().toString();
                final String SDT = edtSoDienThoaiDC.getText().toString();
                final String SLDat = edtslbanDC.getText().toString();
                final String NgayDat = edtngaydat.getText().toString();
                final String GioDat = edtgioDC.getText().toString();
                databaseDatCho = new DatabaseDatCho(getApplicationContext());
                DatCho user = new DatCho(HoTen,SDT,SLDat, NgayDat,GioDat,mAuth.getUid());

            //    DatabaseDatCho.insert(user);
                Toast.makeText(getApplicationContext(), "Thành Công.", Toast.LENGTH_SHORT).show();
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
    public void add() {
        if(filePath!=null){

            final StorageReference imageFolder = storageReference.child("DatCho/"+UUID.randomUUID().toString());
            imageFolder.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            DatCho theLoai = new DatCho();
                            theLoai.setHoTen(edtHotenDC.getText().toString());
                            theLoai.setSoDienThoai(edtSoDienThoaiDC.getText().toString());
                            theLoai.setSLBan(edtslbanDC.getText().toString());
                            theLoai.setNgayDat(edtngaydat.getText().toString());
                            theLoai.setGioDC(edtgioDC.getText().toString());
                            theLoai.setToken(mAuth.getUid());
                            databaseDatCho = new DatabaseDatCho(getApplicationContext());
                            databaseDatCho.insert(theLoai);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Thành Công"+e.getMessage(),Toast.LENGTH_SHORT ).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (
                 resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();

        }
    }

}
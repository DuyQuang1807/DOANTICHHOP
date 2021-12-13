package com.app.cafe.view;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cafe.CallBack.UserCallBack;
import com.app.cafe.Databases.DatabaseUser;
import com.app.cafe.Model.User;
import com.app.cafe.R;
import com.app.cafe.trangchu;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class editprofile extends AppCompatActivity {

    Button btnCapNhat;
    EditText edtDiaChi,edtSoDienThoai,edtHoten;
    TextView edtEmail;

    DatabaseUser databaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    String mail, phone, diachi, anh, pass,ten;
    CircleImageView imgProfile;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        btnCapNhat=findViewById(R.id.btnDatCho);
        edtDiaChi=findViewById(R.id.edtDiaChi);
        edtEmail=findViewById(R.id.edtEmail);
        edtSoDienThoai=findViewById(R.id.edtSoDienThoai);
        edtHoten = findViewById(R.id.edtHoten);
        imgProfile = findViewById(R.id.imgProfile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseUser = new DatabaseUser(getApplicationContext());
        databaseUser.getAll(new UserCallBack() {
            @Override
            public void onSuccess(ArrayList<User> lists) {
                for (int i=0; i < lists.size(); i++){
                    if (lists.get(i).getToken()!=null && lists.get(i).getToken().equalsIgnoreCase(firebaseUser.getUid())) {
//                        name = lists.get(i).getName();
                        diachi = lists.get(i).getDiachi();
                        mail = lists.get(i).getEmail();
                        phone = lists.get(i).getPhone();
                        pass = lists.get(i).getPassword();
                        anh = lists.get(i).getImage();
                        ten = lists.get(i).getName();
//                        ngaysinh = lists.get(i).getNgaysinh();
//                        gioitinh = lists.get(i).getGioitinh();
                    }
                }
                edtDiaChi.setText(diachi);
                edtEmail.setText(mail);
                edtHoten.setText(ten);
                edtSoDienThoai.setText(phone);
                if (anh == null) {
                    Picasso.get().load("https://vnn-imgs-a1.vgcloud.vn/image1.ictnews.vn/_Files/2020/03/17/trend-avatar-1.jpg").into(imgProfile);
                }else if (anh !=null){
                    Picasso.get().load(anh).into(imgProfile);
                }
            }

            @Override
            public void onError(String message) {

            }
        });

        /*Hàm thêm ảnh*/

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        /*End Hàm thêm ảnh*/

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath != null) {
                    String email = edtEmail.getText().toString().trim(); //
                    String phone = edtSoDienThoai.getText().toString().trim();
                    String ten = edtHoten.getText().toString().trim();
                    String diachi = edtDiaChi.getText().toString().trim();
//                    String ngaysinh = edtNgaySinh.getText().toString().trim();
//                    String gioitinh = txtGioiTinh.getText().toString().trim();
                    if (email.isEmpty() || phone.isEmpty() || diachi.isEmpty() || ten.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                    } else if (!email.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                        Toast.makeText(getApplicationContext(), "Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                    } else if (phone.length() < 10 || phone.length() > 12) {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đúng số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else {
                        change();
                    }
                } else  {
                    String email = edtEmail.getText().toString().trim();
                    String phone = edtSoDienThoai.getText().toString().trim();
                    String ten = edtHoten.getText().toString().trim();
                    String diachi = edtDiaChi.getText().toString().trim();
//                    String ngaysinh = edtNgaySinh.getText().toString().trim();
//                    String gioitinh = txtGioiTinh.getText().toString().trim();
                    if (email.isEmpty() || phone.isEmpty() || diachi.isEmpty()|| ten.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                    } else if (!email.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                        Toast.makeText(getApplicationContext(), "Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                    } else if (phone.length() < 10 || phone.length() > 12) {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đúng số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else {
                        User store = new User();
                        store.setEmail(edtEmail.getText().toString());
                        store.setName(edtHoten.getText().toString());
                        store.setPhone(edtSoDienThoai.getText().toString());
                        store.setDiachi(edtDiaChi.getText().toString());
                        store.setPassword(pass);
//                        store.setDiachi(edtaddress.getText().toString());
//                        store.setNgaysinh(edtNgaySinh.getText().toString());
//                        store.setGioitinh(txtGioiTinh.getText().toString());
                        store.setImage(anh);
                        store.setToken(firebaseUser.getUid());
                        databaseUser = new DatabaseUser(getApplicationContext());
                        databaseUser.update(store);
                        Intent intent = new Intent(getApplicationContext(), trangchu.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        });
    }
    private void change() {

        final StorageReference imageFolder = storageReference.child("Users/" + UUID.randomUUID().toString());
        imageFolder.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        User store = new User();
                        store.setEmail(edtEmail.getText().toString());
                        store.setName(edtHoten.getText().toString());
                        store.setPhone(edtSoDienThoai.getText().toString());
                        store.setDiachi(edtDiaChi.getText().toString());
//                        store.setNgaysinh(edtNgaySinh.getText().toString());
//                        store.setGioitinh(txtGioiTinh.getText().toString());
                        store.setImage(uri.toString());
                        store.setPassword(pass);
                        store.setToken(firebaseUser.getUid());
                        databaseUser = new DatabaseUser(getApplicationContext());
                        databaseUser.update(store);
                        Intent intent = new Intent(getApplicationContext(), trangchu.class);
                        startActivity(intent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getApplicationContext().getContentResolver(),
                                filePath);
                imgProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}
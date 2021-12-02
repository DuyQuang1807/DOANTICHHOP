package com.app.cafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cafe.Databases.DatabaseUser;
import com.app.cafe.Model.User;
import com.app.cafe.view.DangNhapActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class DangKyActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView txtEmail,txtpass,txtname,txtSDT;
    Button btndangky;
    private DatabaseUser DatabaseUser;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtpass = findViewById(R.id.txtpass);
        txtname = findViewById(R.id.txtname);
        txtSDT = findViewById(R.id.txtSDT);
        btndangky = findViewById(R.id.btndangky1);

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email    = txtEmail.getText().toString().trim();
                final String password       = txtpass.getText().toString().trim();
                final String ten       = txtname.getText().toString().trim();
                final String sdt       = txtSDT.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    txtEmail.setError("Bắt buộc");
                    return;
                }
                if (!email.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                    txtEmail.setError("Email không hợp lệ.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    txtpass.setError("Bắt buộc");
                    return;
                }

                if(password.length() < 6){
                    txtpass.setError("Mật khẩu phải lớn hơn 6 ký tự");
                    return;
                }
                if(TextUtils.isEmpty(ten)){
                    txtname.setError("Bắt buộc");
                    return;
                }

                if(TextUtils.isEmpty(sdt)){
                    txtSDT.setError("Bắt buộc");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Đăng ký Thất Bại." ,
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    DatabaseUser = new DatabaseUser(getApplicationContext());
                                    User user = new User(email,password,ten,sdt,null,null,null,null,mAuth.getUid());
                                    DatabaseUser.insert(user);
                                    Toast.makeText(getApplicationContext(), "Đăng Ký Thành Công.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), DangNhapActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}

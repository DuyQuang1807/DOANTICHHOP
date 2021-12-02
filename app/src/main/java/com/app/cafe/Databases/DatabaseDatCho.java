package com.app.cafe.Databases;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.cafe.CallBack.DatChoCallback;
import com.app.cafe.CallBack.UserCallBack;
import com.app.cafe.Model.DatCho;
import com.app.cafe.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseDatCho {
    Context context;
    DatabaseReference mRef;
    String key;
    public DatabaseDatCho(Context context) {
        this.context = context;
        this.mRef = FirebaseDatabase.getInstance().getReference("DatCho");
    }
    public void getAll(final DatChoCallback callback) {
        final ArrayList<DatCho> dataloai = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dataloai.clear();

                    for (DataSnapshot data : snapshot.getChildren()){
                        DatCho datcho = data.getValue(DatCho.class);
                        dataloai.add(datcho);
                    }
                    callback.onSuccess(dataloai);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toString());
            }
        });
    }
    public void insert(DatCho item){
        // push cây theo mã tự tạo
        // string key lấy mã push
        key = mRef.push().getKey();
        //insert theo child mã key setvalue theo item
        mRef.child(key).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               Toast.makeText(context, "Đặt Chổ Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(context, "Sign Up Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean update(final DatCho item){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("hoten").getValue(String.class)!=null && dataSnapshot.child("hoten").getValue(String.class).equalsIgnoreCase(item.getHoTen())){
                        key=dataSnapshot.getKey();
                        mRef.child(key).setValue(item);
                        Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return true;
    }
    public void delete(final String matheloai){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("hoten").getValue(String.class).equalsIgnoreCase(matheloai)){
                        key=dataSnapshot.getKey();
                        mRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Delete Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

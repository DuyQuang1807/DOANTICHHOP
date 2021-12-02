package com.app.cafe.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cafe.DatChoActivity;
import com.app.cafe.R;
import com.app.cafe.trangchu;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mview;
    public Button btnDatcho;



    public ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);


        mview = itemView;

//        btnDatcho = itemView.findViewById(R.id.btnDatCho);
//        btnDatcho.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Tạo đối tượng
//                AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
////Thiết lập tiêu đề
//                b.setTitle("Xác nhận");
//                b.setMessage("Bạn có chắc chắn đặt chổ không ?");
//// Nút Ok
//                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//             //           finish(this);
//                        Toast.makeText(v.getContext(), "Đặt chổ thành công", Toast.LENGTH_SHORT).show();
// //                       itemView.(new Intent(getApplicationContext(), DatChoActivity.class));
////                        finish();
//                    }
//                });
////Nút Cancel
//                b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
////Tạo dialog
//                AlertDialog al = b.create();
////Hiển thị
//                al.show();
//            }
//        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mClickListener.onItemClick(v,getAdapterPosition() );


            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition() );
                return false;
            }
        });
    }

    public void setDetails(Context ctx,String name, String adress, String image){
        TextView mName = mview.findViewById(R.id.tvName);
        TextView mAdress = mview.findViewById(R.id.tvAdress);
        ImageView mImage = mview.findViewById(R.id.tvImage);

        mName.setText(name);
        mAdress.setText(adress);

        Picasso.get().load(image).into(mImage);
    }
    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener{

        void onItemClick(View view,int i);
        void onItemLongClick(View view,int i);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){

        mClickListener = clickListener;
    }

}

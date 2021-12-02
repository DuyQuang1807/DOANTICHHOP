package com.app.cafe.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cafe.DatChoActivity;
import com.app.cafe.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    ArrayList<Model> categoryList;
    Context context;
    String Tag;

    public ItemAdapter(ArrayList<Model> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        Model categories = categoryList.get(position);
        holder.title.setText(categories.getName());
        holder.txtdiachi.setText(categories.getAdress());

        Picasso.get()
                .load(categories.getImage())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("Error : ", e.getMessage());
                    }
                });


        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemAdapter.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("image:", categories.getImage());
                intent.putExtra("name:", categories.getName());
                intent.putExtra("adress:",categories.getAdress());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,txtdiachi,txtgia;
        ProgressBar progressBar;
        CardView cardView,cardView1,card_view4;
        LinearLayout line1;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.tvImage);
            title = itemView.findViewById(R.id.tvName);
            txtdiachi = itemView.findViewById(R.id.tvAdress);

        }
    }
}
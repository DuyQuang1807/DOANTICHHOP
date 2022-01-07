package com.app.cafe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cafe.Model.Cafe;
import com.app.cafe.Model.Model;
import com.app.cafe.R;

import java.util.ArrayList;
import java.util.List;

public class YeuthichAdapter extends RecyclerView.Adapter<YeuthichAdapter.ViewHolder> {

    ArrayList<Cafe> categoryList;
    Context context;
    String Tag;
    public YeuthichAdapter(ArrayList<Cafe> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    private List<Model> mlistItem;

    public YeuthichAdapter(List<Model> mlistItem) {
        this.mlistItem = mlistItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = mlistItem.get(position);
        Cafe categories = categoryList.get(position);
        if (model == null){
            return;
        }
        holder.tvAdress.setText("adress:" +model.getAdress());
        holder.tvName.setText("name:" +model.getName());


    }

    @Override
    public int getItemCount() {
        if (mlistItem != null){
            return categoryList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAdress;
        private ImageView tvImage;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAdress = itemView.findViewById(R.id.tvAdress);
            tvImage = itemView.findViewById(R.id.tvImage);

        }
    }
    public void search(ArrayList<Cafe> filllist){
        categoryList = filllist;
        notifyDataSetChanged();

    }

}

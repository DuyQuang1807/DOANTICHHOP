package com.app.cafe.Model;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Model {
    String name,adress,image;
    HashMap<String,String> img;

    public Model(){}

    public Model(HashMap<String, String> img) {
        this.img = img;
    }

    public HashMap<String, String> getImg() {
        return img;
    }

    public void setImg(HashMap<String, String> img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
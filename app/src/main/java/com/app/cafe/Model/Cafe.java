package com.app.cafe.Model;

public class Cafe {
    private String Tenquan;

    public String getTenquan() {
        return Tenquan;
    }

    public void setTenquan(String tenquan) {
        Tenquan = tenquan;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String Diachi;
    private String image;
    String token;
    public Cafe(){

    }
}

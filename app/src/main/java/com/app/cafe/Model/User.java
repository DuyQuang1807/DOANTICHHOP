package com.app.cafe.Model;

public class   User {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String image;
    private String diachi;
    private String ngaysinh;
    private String gioitinh;
    String token;

    public User(){

    }

    public User(String email, String password, String name, String phone, String image, String diachi, String ngaysinh, String gioitinh, String token) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.diachi = diachi;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() { return gioitinh; }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

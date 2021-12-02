package com.app.cafe.Model;

public class DatCho {
    private String HoTen;
    private String SoDienThoai;
    private String SLBan;
    private String NgayDat;
    private String GioDC;
    String token;
    public DatCho(){
    }

    public DatCho(String hoTen, String soDienThoai, String SLBan, String ngayDat, String gioDC, String token) {
        HoTen = hoTen;
        SoDienThoai = soDienThoai;
        this.SLBan = SLBan;
        NgayDat = ngayDat;
        GioDC = gioDC;
        this.token = token;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getSLBan() {
        return SLBan;
    }

    public void setSLBan(String SLBan) {
        this.SLBan = SLBan;
    }

    public String getNgayDat() {
        return NgayDat;
    }

    public void setNgayDat(String ngayDat) {
        NgayDat = ngayDat;
    }

    public String getGioDC() {
        return GioDC;
    }

    public void setGioDC(String gioDC) {
        GioDC = gioDC;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

package vn.edu.greenacademy.model;

/**
 * Created by GIT on 3/21/2017.
 */

public class KhachSan {
    int ID;
    String Ten;
    String MoTa;
    float DanhGia;
    int SoLuotXem;
    int YeuThich;
    int CheckIn;
    int KhuVucID;
    String LinkAnh;
    float Lat;
    float Lng;
    String Address;
    double Gia;

    public KhachSan() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public float getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(float danhGia) {
        DanhGia = danhGia;
    }

    public int getSoLuotXem() {
        return SoLuotXem;
    }

    public void setSoLuotXem(int soLuotXem) {
        SoLuotXem = soLuotXem;
    }

    public int getYeuThich() {
        return YeuThich;
    }

    public void setYeuThich(int yeuThich) {
        YeuThich = yeuThich;
    }

    public int getCheckIn() {
        return CheckIn;
    }

    public void setCheckIn(int checkIn) {
        CheckIn = checkIn;
    }

    public int getKhuVucID() {
        return KhuVucID;
    }

    public void setKhuVucID(int khuVucID) {
        KhuVucID = khuVucID;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }

    public float getLat() {
        return Lat;
    }

    public void setLat(float lat) {
        Lat = lat;
    }

    public float getLng() {
        return Lng;
    }

    public void setLng(float lng) {
        Lng = lng;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double gia) {
        Gia = gia;
    }
}

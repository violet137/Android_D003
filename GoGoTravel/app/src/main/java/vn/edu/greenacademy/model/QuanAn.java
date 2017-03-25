package vn.edu.greenacademy.model;

/**
 * Created by MSI on 3/25/2017.
 */

public class QuanAn {
    int id,loai_id;
    String ten,mota,diachi;
    int danhgia,soluot,yeuthich,chenkin;
    String link;
    int lat,lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoai_id() {
        return loai_id;
    }

    public void setLoai_id(int loai_id) {
        this.loai_id = loai_id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(int danhgia) {
        this.danhgia = danhgia;
    }

    public int getSoluot() {
        return soluot;
    }

    public void setSoluot(int soluot) {
        this.soluot = soluot;
    }

    public int getYeuthich() {
        return yeuthich;
    }

    public void setYeuthich(int yeuthich) {
        this.yeuthich = yeuthich;
    }

    public int getChenkin() {
        return chenkin;
    }

    public void setChenkin(int chenkin) {
        this.chenkin = chenkin;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }
}

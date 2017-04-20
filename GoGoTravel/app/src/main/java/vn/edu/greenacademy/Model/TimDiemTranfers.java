package vn.edu.greenacademy.Model;

/**
 * Created by DangQuang on 4/18/17.
 */

public class TimDiemTranfers {
    public int Id;
    public int LoaiDiem;
    public String Ten;
    public String Mota;
    public String Hinh;
    public int Like;
    public int DanhGiaSao;
    public String DiaChi;



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getLoaiDiem() {
        return LoaiDiem;
    }

    public void setLoaiDiem(int loaiDiem) {
        LoaiDiem = loaiDiem;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public int getDanhGiaSao() {
        return DanhGiaSao;
    }

    public void setDanhGiaSao(int danhGiaSao) {
        DanhGiaSao = danhGiaSao;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
}

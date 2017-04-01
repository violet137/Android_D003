package vn.edu.greenacademy.gogotravel.Model;

/**
 * Created by User on 3/23/2017.
 */

public class DiaDiemChuyenDiTranfers {
    public int idChuyenDi;
    public int IdNgayChuyenDi;
    public int IdDiaDiem;
    public int LoaiDiaDiem;
    public String NoiDungCheckIn;
    public String NgayCheckIn;
    public int SoLuotLike;
    public int SoLuongAnh;
    public String LinkAnh;
    public String TenDiaDiem;
    public String Address;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getIdChuyenDi() {
        return idChuyenDi;
    }

    public void setIdChuyenDi(int idChuyenDi) {
        this.idChuyenDi = idChuyenDi;
    }

    public int getIdNgayChuyenDi() {
        return IdNgayChuyenDi;
    }

    public void setIdNgayChuyenDi(int idNgayChuyenDi) {
        IdNgayChuyenDi = idNgayChuyenDi;
    }

    public int getIdDiaDiem() {
        return IdDiaDiem;
    }

    public void setIdDiaDiem(int idDiaDiem) {
        IdDiaDiem = idDiaDiem;
    }

    public int getLoaiDiaDiem() {
        return LoaiDiaDiem;
    }

    public void setLoaiDiaDiem(int loaiDiaDiem) {
        LoaiDiaDiem = loaiDiaDiem;
    }

    public String getNoiDungCheckIn() {
        return NoiDungCheckIn;
    }

    public void setNoiDungCheckIn(String noiDungCheckIn) {
        NoiDungCheckIn = noiDungCheckIn;
    }

    public String getNgayCheckIn() {
        return NgayCheckIn;
    }

    public void setNgayCheckIn(String ngayCheckIn) {
        NgayCheckIn = ngayCheckIn;
    }

    public int getSoLuotLike() {
        return SoLuotLike;
    }

    public void setSoLuotLike(int soLuotLike) {
        SoLuotLike = soLuotLike;
    }

    public int getSoLuongAnh() {
        return SoLuongAnh;
    }

    public void setSoLuongAnh(int soLuongAnh) {
        SoLuongAnh = soLuongAnh;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }

    public String getTenDiaDiem() {
        return TenDiaDiem;
    }

    public void setTenDiaDiem(String tenDiaDiem) {
        TenDiaDiem = tenDiaDiem;
    }
}

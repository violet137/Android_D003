package vn.edu.greenacademy.gogotravel.Model;

import java.util.ArrayList;

/**
 * Created by User on 3/23/2017.
 */

public class NgayChuyenDiTranfers {
    public int idNgay;
    public String NgayChuyenDi;
    public int SoLuotLike;
    public int SoLuongAnh;
    public ArrayList<DiaDiemChuyenDiTranfers> arrDiaDiem;

    public int getIdNgay() {
        return idNgay;
    }

    public void setIdNgay(int idNgay) {
        this.idNgay = idNgay;
    }

    public String getNgayChuyenDi() {
        return NgayChuyenDi;
    }

    public void setNgayChuyenDi(String ngayChuyenDi) {
        NgayChuyenDi = ngayChuyenDi;
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

    public ArrayList<DiaDiemChuyenDiTranfers> getArrDiaDiem() {
        return arrDiaDiem;
    }

    public void setArrDiaDiem(ArrayList<DiaDiemChuyenDiTranfers> arrDiaDiem) {
        this.arrDiaDiem = arrDiaDiem;
    }
}

package vn.edu.greenacademy.Model;

/**
 * Created by MyPC on 20/04/2017.
 */

public class DiaDiemMap {

    int iID, iLuotXem, iYeuThich, iCheckIn;
    String sTenDiaDiem, sMoTa, sLoaiMarker, sLinkAnh;
    float fDanhGia;
    double dLat, dLng;

    public int getiID() {
        return iID;
    }

    public void setiID(int iID) {
        this.iID = iID;
    }

    public float getfDanhGia() {
        return fDanhGia;
    }

    public void setfDanhGia(float fDanhGia) {
        this.fDanhGia = fDanhGia;
    }

    public int getiLuotXem() {
        return iLuotXem;
    }

    public void setiLuotXem(int iLuotXem) {
        this.iLuotXem = iLuotXem;
    }

    public int getiYeuThich() {
        return iYeuThich;
    }

    public void setiYeuThich(int iYeuThich) {
        this.iYeuThich = iYeuThich;
    }

    public int getiCheckIn() {
        return iCheckIn;
    }

    public void setiCheckIn(int iCheckIn) {
        this.iCheckIn = iCheckIn;
    }

    public String getsTenDiaDiem() {
        return sTenDiaDiem;
    }

    public void setsTenDiaDiem(String sTenDiaDiem) {
        this.sTenDiaDiem = sTenDiaDiem;
    }

    public String getsMoTa() {
        return sMoTa;
    }

    public void setsMoTa(String sMoTa) {
        this.sMoTa = sMoTa;
    }

    public String getsLoaiMarker() {
        return sLoaiMarker;
    }

    public void setsLoaiMarker(String sLoaiMarker) {
        this.sLoaiMarker = sLoaiMarker;
    }

    public String getsLinkAnh() {
        return sLinkAnh;
    }

    public void setsLinkAnh(String sLinkAnh) {
        this.sLinkAnh = sLinkAnh;
    }

    public double getdLat() {
        return dLat;
    }

    public void setdLat(double dLat) {
        this.dLat = dLat;
    }

    public double getdLng() {
        return dLng;
    }

    public void setdLng(double dLng) {
        this.dLng = dLng;
    }
}

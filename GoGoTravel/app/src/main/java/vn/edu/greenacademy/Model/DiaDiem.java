package vn.edu.greenacademy.Model;

import java.io.Serializable;

/**
 * Created by MyPC on 29/03/2017.
 */

public class DiaDiem implements Serializable {
    public String TenDiadiem, Mota,Diachi, LinkAnh;
    public int Id, SoLuotXem,YeuThich, checkIn, IdKhuVuc;
    public double DanhGia, Lat, Lng;

    public DiaDiem() {
    }
}

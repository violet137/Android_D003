package vn.edu.greenacademy.Model;

/**
 * Created by MyPC on 25/03/2017.
 */

public class userLogin {
    public String userName;
    public String matKhau;
    public int kieuTk;

    public userLogin() {
    }

    public userLogin(String userName, String matKhau, int kieuTk) {
        this.userName = userName;
        this.matKhau = matKhau;
        this.kieuTk = kieuTk;
    }

    public String getUserName() {
        return userName;
    }

    public String getMatKhau() {return matKhau;}

    public int getKieuTk() {
        return kieuTk;
    }
}

package vn.edu.greenacademy.Model;

/**
 * Created by MyPC on 27/03/2017.
 */

public class userLogin {
    String userName, matKhau;
    int kieuTk;

    public userLogin() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getKieuTk() {
        return kieuTk;
    }

    public void setKieuTk(int kieuTK) {
        this.kieuTk = kieuTK;
    }
}

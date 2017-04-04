package vn.edu.greenacademy.model;


public class userLogin {
    public String userName;
    public String matKhau;
    public int kieuTk;
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

    public void setKieuTk(int kieuTk) {
        this.kieuTk = kieuTk;
    }
}

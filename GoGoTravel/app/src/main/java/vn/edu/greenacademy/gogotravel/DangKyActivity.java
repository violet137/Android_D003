package vn.edu.greenacademy.gogotravel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DangKyActivity extends AppCompatActivity {

    EditText etTenDangNhap;
    EditText etMatKhau;
    EditText etTenHienThi;
    Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        etMatKhau = (EditText) findViewById(R.id.etMatKhau);
        etTenDangNhap = (EditText) findViewById(R.id.etTenDangNhap);
        etTenHienThi = (EditText) findViewById(R.id.etTenHienThi);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTenHienThi = etTenHienThi.getText().toString();
                String strTenDangNhap = etTenDangNhap.getText().toString();
                String strMatKhau = etMatKhau.getText().toString();
                if(strTenDangNhap.isEmpty()|| strMatKhau.isEmpty()|| strTenHienThi.isEmpty()){
                    XuatThongBao(getString(R.string.khong_nhap_du_thong_tin));
                }else {
                    if(strMatKhau.length() >= 6 && strMatKhau.length() <=12 ){
                        new SignIn().execute(strTenDangNhap,strMatKhau,strTenHienThi);
                    }
                    else{
                        XuatThongBao(getString(R.string.thong_bao_kiem_tra_mat_khau));
                    }
                }
            }
        });
    }

    class SignIn extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            String nameshow = strings[2];

            try {
                URL url = new URL("http://103.237.147.137:9045/TaiKhoan/DangKy");  //tạo đường dẫn lên server
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.addRequestProperty("Accept","text/json");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");  //config giao thức truyền lên server
                connection.connect();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Username",username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.put("MatKhau",password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.put("TenHienThi",nameshow);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //gửi dữ liệu lên server (từ jsonOject chuyển thành kiểu string trên server)
                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(jsonObject.toString());

                //nhận dữ liệu từ server về
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream input = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader reader = new InputStreamReader(input);
                    BufferedReader buff = new BufferedReader(reader);
                    String result ="";
                    String chunks;
                    while ((chunks = buff.readLine()) != null){
                        //đọc tới khi server trả về null thì dừng
                        result += chunks;
                    }
                    return  result;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //parse dữ liệu từ server về
            String responseString = s;
            JSONObject response = null;
            try {
                response = new JSONObject(responseString);
                int status = response.getInt("Status");
                if(status == 1){
                    XuatThongBao(getString(R.string.thanh_cong));
                    Intent it = new Intent(DangKyActivity.this, LoginActivity.class);
                    startActivity(it);
                }
               else {
                    XuatThongBao(getString(R.string.that_bai));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void XuatThongBao(String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //khởi tạo dialog
        builder.setMessage(s);
        builder.setCancelable(true);
        AlertDialog alert = builder.create(); //tạo dialog
        alert.show(); //hiển thị dialog
    }
}

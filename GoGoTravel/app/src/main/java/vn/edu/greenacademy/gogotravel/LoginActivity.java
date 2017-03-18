package vn.edu.greenacademy.gogotravel;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public class Dangnhap extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            try {
                URL url = new URL("http://103.237.147.137:9045/TaiKhoan/DangNhap");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept","text/json");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Username",params[0]);
                jsonObject.put("MatKhau",params[1]);
                jsonObject.put("KieuTk",params[2]);

                OutputStream out = connection.getOutputStream();
                DataOutputStream data = new DataOutputStream(out);
                data.writeBytes(jsonObject.toString());

                connection.connect();

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader reader = new InputStreamReader(in);
                    BufferedReader buffer = new BufferedReader(reader);
                    String result = "";
                    String chunks;
                    while ((chunks = buffer.readLine()) != null){
                        result += chunks;
                    }
                    return result;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String token = jsonObject.getString("Token");
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");

                if(status == 1){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else if(status == 0){
                    Toast.makeText(LoginActivity.this,"Sai username/password",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package vn.edu.greenacademy.gogotravel;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

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
//import com.facebook.FacebookSdk;


public class LoginFacebookActivity extends AppCompatActivity {

    TextView tvStatus;
    LoginButton btnLogInFacebook;
    private CallbackManager callbackManager;
    ProfilePictureView profilePictureView;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnLogInFacebook = (LoginButton) findViewById(R.id.btnLogInFacebook);
        profilePictureView = (ProfilePictureView) findViewById(R.id.friendProfilePicture);

        btnLogInFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profilePictureView.setProfileId(loginResult.getAccessToken().getUserId());
                new LoginFacebook().execute(loginResult.getAccessToken().getUserId(),null);
                Toast.makeText(LoginFacebookActivity.this, "Success", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancel() {
                Toast.makeText(LoginFacebookActivity.this,"Cancel",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginFacebookActivity.this,"Fail",Toast.LENGTH_LONG).show();
            }
        });
    }
    class LoginFacebook extends AsyncTask<String, String ,String>{
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(LoginFacebookActivity.this,s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String userID = strings[0];
            try {
                URL url = new URL("http://103.237.147.137:9045/TaiKhoan/DangNhap");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.addRequestProperty("Accept","text/json");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Username",strings[0]);
                jsonObject.put("MatKhau",strings[1]);
                jsonObject.put("KieuTk",2);
                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(jsonObject.toString());

                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream is = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(is);
                    BufferedReader buff = new BufferedReader(read);
                    String result ="";
                    String chunks;
                    while ((chunks = buff.readLine())!= null){
                        result += chunks;
                    }
                    return  result;
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
    }


}

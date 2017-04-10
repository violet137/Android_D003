package vn.edu.greenacademy.gogotravel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

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

import vn.edu.greenacademy.Unitl.Constant;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    EditText etUser,etPass;
    Button btnSignup,btnReset,btnLogin;
    ImageButton btnFacebook,btnGoogle;
    ImageView ivLogo;
    LoginButton btnLogInFacebook;
    private CallbackManager callbackManager;
    public static GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.SIGN_IN_GG) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            loginGoogle(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnFacebook = (ImageButton) findViewById(R.id.btnFacebook);
        btnGoogle = (ImageButton) findViewById(R.id.btnGoogle);
        btnSignup = (Button) findViewById(R.id.btnSignup);


        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        ivLogo.setImageResource(R.drawable.icon_login);


        btnReset.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        //Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        btnLogInFacebook = (LoginButton) findViewById(R.id.btnLogInFacebook);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        btnLogInFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                new LoginFacebookGoogle().execute(loginResult.getAccessToken().getUserId(),"","1");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            }
            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this,"Cancel",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this,"Fail",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()){
            case R.id.btnReset:
//                intent = new Intent(LoginActivity.this,ResetActivity.class);
                etPass.setText("");
                etUser.setText("");
                break;
            case R.id.btnLogin:
                new Dangnhap().execute(etUser.getText().toString(),etPass.getText().toString());
                break;
            case R.id.btnFacebook:
                btnLogInFacebook.performClick();
                break;
            case R.id.btnGoogle:
                showProgressDialog();
                intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent, Constant.SIGN_IN_GG);
                break;
            case R.id.btnSignup:
                intent = new Intent(LoginActivity.this,DangKyActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // Giũ đăng nhập
            GoogleSignInResult result = opr.get();
            loginGoogle(result);
//            signOut();
        } else {
            // Khi logout
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    loginGoogle(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this,"Connection Failed", Toast.LENGTH_LONG).show();
    }


    class LoginFacebookGoogle extends AsyncTask<String, String ,String>{
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Intent intent = new Intent(LoginActivity.this,QuanAnActivity.class);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String userID = strings[0];
            try {
                URL url = new URL(Constant.URL_DANG_NHAP);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.addRequestProperty("Accept","text/json");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Username",strings[0]);
                jsonObject.put("MatKhau",strings[1]);
                jsonObject.put("KieuTk",Integer.parseInt(strings[2]));
                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(jsonObject.toString());
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(is);
                    BufferedReader buff = new BufferedReader(read);
                    String result = "";
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
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
    }

    public class Dangnhap extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            try {
                URL url = new URL(Constant.URL_DANG_NHAP);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept","text/json");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setRequestMethod("POST");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Username",params[0]);
                jsonObject.put("MatKhau",params[1]);
                jsonObject.put("KieuTk",0);

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
//                    Intent intent = new Intent(LoginActivity.this,QuanAnActivity.class);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                }else if(status == 0){
                    Toast.makeText(LoginActivity.this,"Sai username/password",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loginGoogle(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Login thành công
            final GoogleSignInAccount acct = result.getSignInAccount();
//            String personName = acct.getDisplayName();
//            Uri personPhotoUrl  = acct.getPhotoUrl();
//            String email = acct.getEmail();
            //id đăng ký account
            String id = acct.getId();
            new LoginFacebookGoogle().execute(id,"","2");
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
//    public void signOut() {
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//                        Toast.makeText(LoginActivity.this,"Log Out", Toast.LENGTH_LONG).show();
//                    }
//                });
//    }

}

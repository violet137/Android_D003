package vn.edu.greenacademy.gogotravel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

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
import java.net.ProtocolException;
import java.net.URL;

import vn.edu.greenacademy.Unitl.Constrant;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    private static boolean checkID = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);


        // Button google đế mặc định
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constrant.SIGN_IN_GG);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(MainActivity.this,"Log Out", Toast.LENGTH_LONG).show();
                        updateUI(false);
                    }
                });
    }
    private void loginGoogle(GoogleSignInResult result) {
        if (result.isSuccess()) {

            // Login thành công
            final GoogleSignInAccount acct = result.getSignInAccount();

            String personName = acct.getDisplayName();
            Uri personPhotoUrl  = acct.getPhotoUrl();
            String email = acct.getEmail();
            //id đăng ký account
            String id = acct.getId();

            new DangNhap().execute(id,"","2");

            if (checkID == false){
                new DangKy().execute(id,"",personName);
                txtName.setText(personName + id);
                txtEmail.setText(email);
            }else {
                txtName.setText(id);
                txtEmail.setText("");
            }

//            if (arrID.size() == 0){
//                checkID = true;
//            }else {
//
//                for (int i = 0; i < arrID.size(); i++) {
//                    if (id.equals(arrID.get(i))){
//                        checkID = false;
//                        break;
//                    }else {
//                        checkID = true;
//                    }
//                }
//            }

            //------
//            if (checkID == false){
//                txtName.setText("Đã đăng nhập rồi");
//                txtEmail.setText("");
//                imgProfilePic.setImageResource(R.drawable.common_full_open_on_phone);
//            }else {
//                arrID.add(id);
//                txtName.setText(personName);
//                txtEmail.setText(email);
//                //Nếu link hình ảnh Null thì lấy hình mặc định
//                if (personPhotoUrl == null){
//                    imgProfilePic.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
//                }else {
//                    Glide.with(getApplicationContext()).load(personPhotoUrl.toString())
//                            .thumbnail(0.5f)
//                            .crossFade()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .into(imgProfilePic);
//                }
//            }
            updateUI(true);
        } else {
            // Signed out
            updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;

            case R.id.btn_sign_out:
                signOut();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Gọi intenr login google
        if (requestCode == Constrant.SIGN_IN_GG) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            loginGoogle(result);
        }
    }

       @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // Giũ đăng nhập
            GoogleSignInResult result = opr.get();
            loginGoogle(result);
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
        Toast.makeText(MainActivity.this,"Login Faild", Toast.LENGTH_LONG).show();
    }

    public class DangNhap extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("http://103.237.147.137:9045/TaiKhoan/DangNhap");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //kieu du lieu server tra ve
                connection.addRequestProperty("Accept", "text/json"); // dinh nghia du lieu tu server ve client
                connection.addRequestProperty("Content-Type", "application/json"); // dinh nghia du lieu tu client len server

                //giao thuc truyen len server GET - POST - PUT - DELETE
                connection.setRequestMethod("POST");


                //mo ket noi den server
                connection.connect();

                JSONObject jsonObject = new JSONObject();
                try {
                    //JsonObject : tuong ung {}
                    //JsonArray : tuong ung []
                    jsonObject.put("Username", strings[0]);
                    jsonObject.put("MatKhau", strings[1]);
                    jsonObject.put("KieuTK",Integer.parseInt(strings[2]));

                    //DataOutputStream : gui du lieu len server
                    DataOutputStream data = new DataOutputStream(connection.getOutputStream());
                    data.writeBytes(jsonObject.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){ // lay poncode kiem tra tu server

                    InputStream it = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);

                    String result = "";
                    String chenks;

                    while ((chenks = buff.readLine()) != null){
                        //doc den khi nao server tra ve null thi dung
                        result += chenks;
                    }

                    return result;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            lay respone tu server tra ve
            String serever_respone = "";
            try {
                JSONObject jsonObject_server = new JSONObject(s);
                String token = jsonObject_server.getString("Token");
                int status = jsonObject_server.getInt("Status");
                String description = jsonObject_server.getString("Description");

                if (status == 0){
                    checkID = false;
                }else {
                    checkID = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class DangKy extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            URL url = null;
            try {
                url = new URL("http://103.237.147.137:9045/TaiKhoan/DangKy");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //kieu du lieu server tra ve
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");

                //giao thuc truyen len server GET - POST - PUT - DELETE
                connection.setRequestMethod("POST");

                connection.connect();
                JSONObject jsonObject = new JSONObject();
                try {
                    //JsonObject : tuong ung {}
                    //JsonArray : tuong ung []
                    jsonObject.put("Username", strings[0]);
                    jsonObject.put("MatKhau", strings[1]);
                    jsonObject.put("TenHienThi",strings[2]);

                    //DataOutputStream : gui du lieu len server
                    OutputStream outputStream = connection.getOutputStream();
                    DataOutputStream data = new DataOutputStream(outputStream);
                    data.writeBytes(jsonObject.toString());

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){ // lay poncode kiem tra tu server

                        InputStream it = new BufferedInputStream(connection.getInputStream());
                        InputStreamReader read = new InputStreamReader(it);
                        BufferedReader buff = new BufferedReader(read);

                        String result = "";
                        String chenks;

                        while ((chenks = buff.readLine()) != null){
                            //doc den khi nao server tra ve null thi dung
                            result += chenks;
                        }
                        return result;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

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

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);
        }
    }
}

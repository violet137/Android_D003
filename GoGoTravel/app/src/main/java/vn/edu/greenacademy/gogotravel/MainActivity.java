package vn.edu.greenacademy.gogotravel;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.ArrayList;

import vn.edu.greenacademy.Model.userLogin;
import vn.edu.greenacademy.Unitl.Constrant;
import vn.edu.greenacademy.Unitl.checkLogin;
import vn.edu.greenacademy.Unitl.loginGmail;


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
    private ArrayList<String> arrID;

    loginGmail gmail;
    checkLogin check;

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

        arrID = new ArrayList();

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);


        gmail = new loginGmail();

        gmail.callLoginGmail();

        // Button google đế mặc định
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                gmail.signIn();
                break;

            case R.id.btn_sign_out:
                gmail.signOut();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Gọi intenr login google
        if (requestCode == Constrant.SIGN_IN_GG) {
            ArrayList<String> arrUserInfo = gmail.result_login(data);
            if (arrUserInfo != null){
                userLogin userGmail = new userLogin(arrUserInfo.get(0), "", 2);
                login(userGmail);
            }
        }

    }

    //xử lý dăng ký
    private void login(userLogin user) {
        check = new checkLogin(user);
        check.execute(Constrant.URL_LOGIN);
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // Giũ đăng nhập
//            gmail.result_login(MainActivity.this);
        } else {
            // Khi logout
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
//                    loginGoogle(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MainActivity.this,"Login Faild", Toast.LENGTH_LONG).show();
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

package vn.edu.greenacademy.gogotravel;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.IOException;

import vn.edu.greenacademy.Unitl.Constrant;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    public  String accessToken = "";

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

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);

        //gọi api của google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Button google đế mặc định
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
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

            //id đăng ký account & token season
            String id = acct.getId();

            new RetrieveTokenTask().execute(personName);


            txtName.setText(personName + "\n" + id + "\n" + accessToken);
            txtEmail.setText(email);

            //Nếu link hình ảnh Null thì lấy hình mặc định

            if (personPhotoUrl == null){
                imgProfilePic.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
            }else {
                Glide.with(getApplicationContext()).load(personPhotoUrl.toString())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgProfilePic);
            }

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

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String accountName = strings[0];
            String scope = "oauth2:" + Scopes.PROFILE + " " + "email";
            Context context = getApplicationContext();
            String accessToken = "";

            try {
                accessToken = GoogleAuthUtil.getToken (context, accountName, scope);
            } catch (IOException e) {
                 e.getMessage();
            } catch (UserRecoverableAuthException e) {
//                startActivityForResult (e.getIntent(), Constrant.REQUEST_CODE_TOKEN_AUTH);
            } catch (GoogleAuthException e) {
                e.getMessage();

            }
            return accessToken;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            accessToken = s;
        }
    };


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

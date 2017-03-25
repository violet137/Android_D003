package vn.edu.greenacademy.Unitl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

import vn.edu.greenacademy.Model.userLogin;

/**
 * Created by MyPC on 25/03/2017.
 */

public class loginGmail {

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    GoogleSignInAccount googleSignInAccount;
    AppCompatActivity activity;

    ArrayList<userLogin> arrUserLogin;


    public void callLoginGmail(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, Constrant.SIGN_IN_GG);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(activity,"Log Out", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public ArrayList<String> result_login(Intent intent) {
        ArrayList<String> arrUser = new ArrayList<>();
        // Đối tượng kết quả
        GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);

        if (googleSignInResult.isSuccess()) {

            //lấy thông tin tài khoản
            googleSignInAccount = googleSignInResult.getSignInAccount();

            //lấy dữ liêu google
            String userName = googleSignInAccount.getDisplayName();
            String userEmail = googleSignInAccount.getEmail();
            String userId = googleSignInAccount.getId();
            Uri userUriPhoto = googleSignInAccount.getPhotoUrl();


            //gán giá trị
            if (userUriPhoto != null){
                String userPhoto = userUriPhoto.toString();
                arrUser.add(userId);
                arrUser.add(userName);
                arrUser.add(userEmail);
                arrUser.add(userPhoto);
            } else {
                arrUser.add(userId);
                arrUser.add(userName);
                arrUser.add(userEmail);
            }
        }
        return arrUser;
    }


}

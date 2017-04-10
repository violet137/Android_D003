package vn.edu.greenacademy.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import vn.edu.greenacademy.gogotravel.LoginActivity;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaiKhoanFragment extends Fragment {

    Button btnSignOut;

    public static TaiKhoanFragment instance;

    public static TaiKhoanFragment getInstance(){
        if (instance == null){
            instance = new TaiKhoanFragment();
        }

        return instance;
    }

    public TaiKhoanFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(LoginActivity.mGoogleApiClient);
        if (opr.isDone()) {
            // Giũ đăng nhập
//            GoogleSignInResult result = opr.get();
//            loginGoogle(result);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        // Inflate the layout for this fragment

        btnSignOut = (Button) v.findViewById(R.id.btnSignOut);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        return v;
    }
//    private void loginGoogle(GoogleSignInResult result) {
//        if (result.isSuccess()) {
//            final GoogleSignInAccount acct = result.getSignInAccount();
//            String id = acct.getId();
//        }
//    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(LoginActivity.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

}

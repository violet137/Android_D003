package vn.edu.greenacademy.gogotravel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import vn.edu.greenacademy.Fragment.BanDoFragment;
import vn.edu.greenacademy.Fragment.HanhTrinhFragment;
import vn.edu.greenacademy.Fragment.KhuVucFragment;
import vn.edu.greenacademy.Fragment.TaiKhoanFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    FrameLayout flMain;

    KhuVucFragment khuVucFragment;
    BanDoFragment banDoFragment;
    TaiKhoanFragment taiKhoanFragment;
    HanhTrinhFragment hanhTrinhFragment;
    ImageButton ibHome, ibBanDo, ibhanhTrinh, ibTaiKhoan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        flMain = (FrameLayout) findViewById(R.id.flMain);

        ibHome = (ImageButton) findViewById(R.id.ibHome);
        ibBanDo = (ImageButton) findViewById(R.id.ibBanDo);
        ibhanhTrinh = (ImageButton) findViewById(R.id.ibhanhTrinh);
        ibTaiKhoan = (ImageButton) findViewById(R.id.ibTaiKhoan);

        khuVucFragment = KhuVucFragment.getInstance();
        banDoFragment = BanDoFragment.getInstance();
        taiKhoanFragment = TaiKhoanFragment.getInstance();
        hanhTrinhFragment = HanhTrinhFragment.getInstance();
        callFragment(khuVucFragment);

        ibHome.setOnClickListener(this);
        ibBanDo.setOnClickListener(this);
        ibhanhTrinh.setOnClickListener(this);
        ibTaiKhoan.setOnClickListener(this);
    }

    public void callFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.flMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibHome:
                callFragment(khuVucFragment);
                break;
            case R.id.ibBanDo:
                callFragment(banDoFragment);
                break;
            case R.id.ibhanhTrinh:
                callFragment(hanhTrinhFragment);
                break;
            case R.id.ibTaiKhoan:
                callFragment(taiKhoanFragment);
                break;
        }
    }
}

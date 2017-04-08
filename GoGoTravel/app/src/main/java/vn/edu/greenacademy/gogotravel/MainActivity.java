package vn.edu.greenacademy.gogotravel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import vn.edu.greenacademy.Fragment.KhuVucFragment;


public class MainActivity extends AppCompatActivity {

    KhuVucFragment khuVucFragment;
    FrameLayout flMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        flMain = (FrameLayout) findViewById(R.id.flMain);
        khuVucFragment = KhuVucFragment.getInstance();
        callFragment(khuVucFragment);
    }

    public void callFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.flMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

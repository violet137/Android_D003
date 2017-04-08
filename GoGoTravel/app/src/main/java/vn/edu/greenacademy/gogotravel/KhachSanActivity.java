package vn.edu.greenacademy.gogotravel;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vn.edu.greenacademy.fragment.KhachSanFragment;


public class KhachSanActivity extends AppCompatActivity {

    KhachSanFragment khachSanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_khach_san);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, new KhachSanFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

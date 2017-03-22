package vn.edu.greenacademy.gogotravel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.edu.greenacademy.fragment.KhachSanFragment;

public class KhachSanActivity extends AppCompatActivity {

    KhachSanFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_san);
        fragment = new KhachSanFragment();

    }
}

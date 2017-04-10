package vn.edu.greenacademy.gogotravel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.edu.greenacademy.gogotravel.Fragment.HanhTrinhFragment;

public class HanhTrinhActivity extends AppCompatActivity {

    HanhTrinhFragment hanhTrinhFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment hanhtrinhFragment = new HanhTrinhFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_hanhtrinh,hanhtrinhFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}

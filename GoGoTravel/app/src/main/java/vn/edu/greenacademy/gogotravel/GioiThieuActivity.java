package vn.edu.greenacademy.gogotravel;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import vn.edu.greenacademy.adapter.MyAdapter;

import static vn.edu.greenacademy.gogotravel.R.layout.activity_main;

public class GioiThieuActivity extends AppCompatActivity {

    ViewPager viewPager;
    View view;
    private MyAdapter mAdapter;
    private ImageView[] dots;

    private int[] mImageView = {
            R.drawable.tutorial_1,
            R.drawable.tutorial_2,
            R.drawable.tutorial_3,
            R.drawable.tutorial_4
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        setReference();
        viewPager = (ViewPager) findViewById(R.id.view);

    }

    private void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.);
    }

}

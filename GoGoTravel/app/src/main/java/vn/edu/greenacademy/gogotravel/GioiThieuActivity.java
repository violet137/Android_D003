package vn.edu.greenacademy.gogotravel;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import vn.edu.greenacademy.adapter.MyAdapter;
import vn.edu.greenacademy.fragment.Hinh4Fragment;

import static vn.edu.greenacademy.gogotravel.R.layout.activity_main;

public class GioiThieuActivity extends AppCompatActivity {

    ViewPager viewPager;
    View view;
    private MyAdapter mAdapter;
    private ImageView[] dots;
    Button btnNext;
    private int dotsCount;

    private int[] mImageView = {
            R.drawable.tutorial_1,
            R.drawable.tutorial_2,
            R.drawable.tutorial_3,
            R.drawable.tutorial_4
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view);
        btnNext = (Button) findViewById(R.id.btnNext);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem()==2){
                    btnNext.setText("Bắt đầu");
                }
                if (viewPager.getCurrentItem()==3){
                    Intent intent = new Intent(GioiThieuActivity.this, ManHinhChoActivity.class);
                    GioiThieuActivity.this.startActivity(intent);
                }else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });
    }






}

package vn.edu.greenacademy.gogotravel;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import vn.edu.greenacademy.Adapter.MyAdapter;

public class GioiThieuActivity extends AppCompatActivity {

    ViewPager viewPager;
    private MyAdapter mAdapter;
    Button btnNext;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioithieu);
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
                    Intent intent = new Intent(GioiThieuActivity.this, CameraActivity.class);
                    GioiThieuActivity.this.startActivity(intent);
                }else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });
    }






}

package vn.edu.greenacademy.gogotravel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.edu.greenacademy.Adapter.MyAdapter;

public class GioiThieuActivity extends AppCompatActivity {

    ViewPager viewPager;
    private MyAdapter mAdapter;
    Button btnNext;
    SharedPreferences pre;
    boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gioithieu_activity);
        viewPager = (ViewPager) findViewById(R.id.view);
        btnNext = (Button) findViewById(R.id.btnNext);


        pre=getSharedPreferences("count", MODE_PRIVATE);
        final SharedPreferences.Editor edit=pre.edit();
        check = pre.getBoolean("check",false);
        if (check){
            chuyenTrang();
        }else {
            mAdapter = new MyAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mAdapter);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem()==2){
                    btnNext.setText("Bắt đầu");
                }
                if (viewPager.getCurrentItem()==3){
                    edit.putBoolean("check", true);
                    edit.commit();
                   chuyenTrang();
                }else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });
    }
    private void chuyenTrang(){
        Intent intent = new Intent(GioiThieuActivity.this, LoginActivity.class);
        GioiThieuActivity.this.startActivity(intent);
    }
}

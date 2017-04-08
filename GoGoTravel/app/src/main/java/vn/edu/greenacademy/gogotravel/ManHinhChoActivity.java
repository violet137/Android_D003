package vn.edu.greenacademy.gogotravel;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ManHinhChoActivity extends AppCompatActivity {
    ImageView ivCho;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cho);

        ivCho = (ImageView) findViewById(R.id.ivCho);
        ivCho.setImageResource(R.drawable.flash_screen);

        handler.postDelayed(updateTime,2000);
    }

    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(ManHinhChoActivity.this, GioiThieuActivity.class);
            startActivity(intent);

        }
    };




}

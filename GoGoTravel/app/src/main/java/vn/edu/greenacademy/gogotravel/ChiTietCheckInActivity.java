package vn.edu.greenacademy.gogotravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ChiTietCheckInActivity extends AppCompatActivity {

    TextView tvTenDiaDiem, tvNoiDung, tvThoiGian;
    ImageView imvHinhAnh;
    Button btnGioiThieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_check_in);

        String tendiadiem = getIntent().getStringExtra("tendiadiem");
        String thoigian = getIntent().getStringExtra("thoigian");
        String linkanh = getIntent().getStringExtra("linkanh");
        String noidung = getIntent().getStringExtra("noidung");

        tvTenDiaDiem = (TextView) findViewById(R.id.tvTenDiaDiemCheckIn);
        tvNoiDung = (TextView) findViewById(R.id.tvNoiDungCheckIn);
        imvHinhAnh = (ImageView) findViewById(R.id.imvHinhAnhChiTiet);
        tvThoiGian = (TextView) findViewById(R.id.tvThoiGianCheckIn);
        btnGioiThieu = (Button) findViewById(R.id.btnGioiThieu);

        tvTenDiaDiem.setText(tendiadiem);
        tvThoiGian.setText(thoigian);
        tvNoiDung.setText(noidung);
        Picasso.with(ChiTietCheckInActivity.this).load(linkanh).resize(260,200).into(imvHinhAnh);

        btnGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chuyen man hinh
            }
        });

    }
}

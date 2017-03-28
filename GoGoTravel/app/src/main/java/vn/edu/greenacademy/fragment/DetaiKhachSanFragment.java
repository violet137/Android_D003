package vn.edu.greenacademy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.greenacademy.adapter.KhachSanAdapter;
import vn.edu.greenacademy.asynctask.Image;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.model.KhachSans;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetaiKhachSanFragment extends Fragment {

    ImageView imageKhachSan;
    TextView tvTenKhachSan;
    TextView tvDiaChi;
    TextView tvDanhGia;
    TextView tvSoLuotYeuThich;
    TextView tvSoLuotCheckIn;
    TextView tvSoLuotXem;
    TextView tvGiaTien;
    TextView tvMoTa;

    public DetaiKhachSanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detai_khach_san, container, false);

        imageKhachSan = (ImageView) view.findViewById(R.id.imageHotel);
        tvDanhGia = (TextView) view.findViewById(R.id.tvDanhGia);
        tvDiaChi = (TextView) view.findViewById(R.id.tvAddress);
        tvGiaTien = (TextView) view.findViewById(R.id.tvGia);
        tvSoLuotCheckIn = (TextView) view.findViewById(R.id.tvCheckIn);
        tvSoLuotYeuThich = (TextView) view.findViewById(R.id.tvYeuThich);
        tvSoLuotXem = (TextView) view.findViewById(R.id.tvSoLuotXem);
        tvTenKhachSan = (TextView) view.findViewById(R.id.tvNameHotel);
        tvMoTa = (TextView) view.findViewById(R.id.tvMoTa);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            KhachSans ks = (KhachSans) bundle.getSerializable("KhachSan");
            new Image(ks.getLinkAnh(),imageKhachSan).execute();
            tvSoLuotCheckIn.setText(String.valueOf(ks.getCheckIn()));
            tvDanhGia.setText(String.valueOf(ks.getDanhGia()));
            tvSoLuotXem.setText(String.valueOf(ks.getSoLuotXem()));
            tvDiaChi.setText(ks.getAddress());
            tvSoLuotYeuThich.setText(String.valueOf(ks.getYeuThich()));
            tvGiaTien.setText(String.valueOf(ks.getGia()));
            tvTenKhachSan.setText(ks.getTen());
            tvMoTa.setText(ks.getMoTa());
        }
        return view;
    }

}

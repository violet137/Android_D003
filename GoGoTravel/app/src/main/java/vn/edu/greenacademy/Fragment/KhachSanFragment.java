package vn.edu.greenacademy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KhachSanFragment extends Fragment {

    ImageView imageKhachSan;
    TextView tvTenKhachSan;
    TextView tvDiaChi;
    TextView tvDanhGia;
    TextView tvSoLuotYeuThich;
    TextView tvSoLuotCheckIn;
    TextView tvSoLuotXem;
    TextView tvGiaTien;


    public KhachSanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_khach_san, container, false);
        imageKhachSan = (ImageView) view.findViewById(R.id.imageKhachSan);
        tvDanhGia = (TextView) view.findViewById(R.id.tvDanhGia);
        tvDiaChi = (TextView) view.findViewById(R.id.tvDiaChi);
        tvGiaTien = (TextView) view.findViewById(R.id.tvGiaTien);
        tvSoLuotCheckIn = (TextView) view.findViewById(R.id.tvSoLuotCheckIn);
        tvSoLuotYeuThich = (TextView) view.findViewById(R.id.tvSoLuotYeuThich);
        tvSoLuotXem = (TextView) view.findViewById(R.id.tvSoLuotXem);
        tvTenKhachSan = (TextView) view.findViewById(R.id.tvTenKhachSan);
        return view;
    }

}

package vn.edu.greenacademy.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.edu.greenacademy.Model.DiaDiemMap;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogFragment extends DialogFragment implements View.OnClickListener {

    private static CustomDialogFragment instance = null;

    TextView tvTen, tvMoTa, tvLuotXem, tvYeuThich, tvCheckIn;
    ImageView ivHinhAnh;
    RatingBar ratingBar;
    Button btnChiDuong, btnHuy;

    public CustomDialogFragment() {
        // Required empty public constructor
    }

    public static CustomDialogFragment getInstance(DiaDiemMap diadiem)
    {
        if(instance == null)
            instance = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString("ID", String.valueOf(diadiem.getiID()));
        args.putString("Ten", String.valueOf(diadiem.getsTenDiaDiem()));
        args.putString("MoTa", String.valueOf(diadiem.getsMoTa()));
        args.putString("LuotXem", String.valueOf(diadiem.getiLuotXem()));
        args.putString("YeuThich", String.valueOf(diadiem.getiYeuThich()));
        args.putString("CheckIn", String.valueOf(diadiem.getiCheckIn()));
        args.putString("DanhGia", String.valueOf(diadiem.getfDanhGia()));
        args.putString("LinkAnh", String.valueOf(diadiem.getsLinkAnh()));
        args.putString("Lat", String.valueOf(diadiem.getdLat()));
        args.putString("Lng", String.valueOf(diadiem.getdLng()));
        instance.setArguments(args);
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.custom_dialog, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        tvTen = (TextView) view.findViewById(R.id.tvTen);
        tvMoTa = (TextView) view.findViewById(R.id.tvMoTa);
        tvLuotXem = (TextView) view.findViewById(R.id.tvLuotXem);
        tvYeuThich = (TextView) view.findViewById(R.id.tvYeuThich);
        tvCheckIn = (TextView) view.findViewById(R.id.tvCheckIn);
        ivHinhAnh = (ImageView) view.findViewById(R.id.ivHinhAnh);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        btnChiDuong = (Button) view.findViewById(R.id.btnChiDuong);
        btnHuy = (Button) view.findViewById(R.id.btnHuy);

        tvTen.setText("Tên: " + getArguments().getString("Ten").toString());
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            tvMoTa.setText(Html.fromHtml(getArguments().get("MoTa").toString(),Html.FROM_HTML_MODE_LEGACY));
        }else{
            tvMoTa.setText(Html.fromHtml(getArguments().get("MoTa").toString()));
        }
        tvLuotXem.setText("Lượt xem: " + getArguments().getString("LuotXem").toString());
        tvYeuThich.setText("Yêu thích: " + getArguments().getString("YeuThich").toString());
        tvCheckIn.setText("Check In: " + getArguments().getString("CheckIn").toString());
        ratingBar.setRating(Float.parseFloat(getArguments().getString("DanhGia").toString())/2.0f);
        Picasso.with(getContext()).load(getArguments().getString("LinkAnh")).into(ivHinhAnh);

        btnChiDuong.setOnClickListener(this);
        btnHuy.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnChiDuong:

                break;
            case R.id.btnHuy:
                this.dismiss();
                break;
        }
    }
}

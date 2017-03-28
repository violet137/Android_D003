package vn.edu.greenacademy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import vn.edu.greenacademy.asynctask.Image;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.model.KhachSans;

/**
 * Created by User on 3/22/2017.
 */

public class KhachSanAdapter extends BaseAdapter{

    private Context context= null;
    LayoutInflater mLayoutinlater;
    List<KhachSans> arrHotel;
    int layout;

    ImageView imageKhachSan;
    TextView tvTenKhachSan;
    TextView tvDiaChi;
    TextView tvDanhGia;
    TextView tvSoLuotYeuThich;
    TextView tvSoLuotCheckIn;
    TextView tvSoLuotXem;
    TextView tvGiaTien;


    public void ReloadData(List<KhachSans> arrHotel){
        this.arrHotel.addAll(arrHotel);
        notifyDataSetChanged();
    }


    public KhachSanAdapter(Context context, int layout, List<KhachSans> arrHotel) {
        this.context = context;
        this.layout = layout;
        this.arrHotel = arrHotel;
        mLayoutinlater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrHotel.size();
    }

    @Override
    public Object getItem(int i) {
        return arrHotel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = mLayoutinlater.inflate(R.layout.item_khachsan_layout,null);
            imageKhachSan = (ImageView) view.findViewById(R.id.imageKhachSan);
            tvDanhGia = (TextView) view.findViewById(R.id.tvDanhGia);
            tvDiaChi = (TextView) view.findViewById(R.id.tvDiaChi);
            tvGiaTien = (TextView) view.findViewById(R.id.tvGiaTien);
            tvSoLuotCheckIn = (TextView) view.findViewById(R.id.tvSoLuotCheckIn);
            tvSoLuotYeuThich = (TextView) view.findViewById(R.id.tvSoLuotYeuThich);
            tvSoLuotXem = (TextView) view.findViewById(R.id.tvSoLuotXem);
            tvTenKhachSan = (TextView) view.findViewById(R.id.tvTenKhachSan);
        }
        KhachSans khachSans = (KhachSans) getItem(i);

        new Image(khachSans.getLinkAnh(),imageKhachSan).execute();
        tvSoLuotCheckIn.setText(String.valueOf(khachSans.getCheckIn()));
        tvDanhGia.setText(String.valueOf(khachSans.getDanhGia()));
        tvSoLuotXem.setText(String.valueOf(khachSans.getSoLuotXem()));
        tvDiaChi.setText(khachSans.getAddress());
        tvSoLuotYeuThich.setText(String.valueOf(khachSans.getYeuThich()));
        tvGiaTien.setText(String.valueOf(khachSans.getGia()));
        tvTenKhachSan.setText(khachSans.getTen());
        return view;
    }

}

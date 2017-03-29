package vn.edu.greenacademy.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.greenacademy.Model.DiaDiem;
import vn.edu.greenacademy.Until.DownloadImageTask;
import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by MyPC on 29/03/2017.
 */

public class DiaDiemAdapter extends ArrayAdapter {

    Activity con;
    int layout;
    ArrayList<DiaDiem> arrDiaDiem;


    public DiaDiemAdapter(Activity context, int resource, ArrayList<DiaDiem> objects) {
        super(context, resource, objects);

        this.con = context;
        this.layout = resource;
        this.arrDiaDiem = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = con.getLayoutInflater();
        convertView = inflater.inflate(layout, null);
        final DiaDiem diaDiem = arrDiaDiem.get(position);

        TextView tvTenDiaDiem = (TextView) convertView.findViewById(R.id.tvTenDiaDiem);
        TextView tvMotaDiaDiem = (TextView) convertView.findViewById(R.id.tvMoTaDiaDiem);
        TextView tvDanhgiaDiaDiem = (TextView) convertView.findViewById(R.id.tvDanhGiaDiaDiem);
        TextView tvSoLuotXemDiaDiem = (TextView) convertView.findViewById(R.id.tvSoLuotXemDiaDiem);
        TextView tvYeuThichDiaDiem = (TextView) convertView.findViewById(R.id.tvYeuThichDiaDiem);
        ImageView ivLinkAnhDiaDiem = (ImageView) convertView.findViewById(R.id.ivLinkAnhDiaDiem);
        TextView tvCheckInDiaDiem = (TextView) convertView.findViewById(R.id.tvCheckInDiaDiem);
        TextView tvDiaChiDiaDiem = (TextView) convertView.findViewById(R.id.tvDiaChiDiaDiem);

        new DownloadImageTask(ivLinkAnhDiaDiem).execute(diaDiem.LinkAnh);
        tvTenDiaDiem.setText(diaDiem.TenDiadiem);
        tvMotaDiaDiem.setText(diaDiem.Mota);
        tvDanhgiaDiaDiem.setText(String.valueOf(diaDiem.DanhGia));
        tvSoLuotXemDiaDiem.setText(String.valueOf(diaDiem.SoLuotXem));
        tvYeuThichDiaDiem.setText(String.valueOf(diaDiem.YeuThich));
        tvCheckInDiaDiem.setText(String.valueOf(diaDiem.checkIn));
        tvDiaChiDiaDiem.setText(diaDiem.Diachi);

        convertView.setBackgroundResource(R.drawable.border_dia_diem);

        return convertView;
    }




}

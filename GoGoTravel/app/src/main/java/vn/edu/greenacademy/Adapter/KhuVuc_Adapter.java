package vn.edu.greenacademy.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.greenacademy.Model.KhuVuc;
import vn.edu.greenacademy.Until.DownloadImageTask;
import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by GIT on 3/25/2017.
 */

public class KhuVuc_Adapter extends ArrayAdapter {

    Activity con;
    int layout;
    ArrayList<KhuVuc> arrKhuVuc;

    public KhuVuc_Adapter(Activity context, int resource, ArrayList<KhuVuc> objects) {

        super(context, resource, objects);

        this.con = context;
        this.layout = resource;
        this.arrKhuVuc = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = con.getLayoutInflater();
        convertView = inflater.inflate(layout, null);
        final KhuVuc khuvuc = arrKhuVuc.get(position);


        TextView tvTen_KhuVuc = (TextView) convertView.findViewById(R.id.tvTen_KhuVuc);
        TextView tvMota_KhuVuc = (TextView) convertView.findViewById(R.id.tvMoTa_KhuVuc);
        TextView tvDanhgia_KhuVuc = (TextView) convertView.findViewById(R.id.tvDanhGia_KhuVuc);
        TextView tvSoLuotXem_KhuVuc = (TextView) convertView.findViewById(R.id.tvSoLuotXem_KhuVuc);
        TextView tvYeuThich_KhuVuc = (TextView) convertView.findViewById(R.id.tvYeuThich_KhuVuc);
        ImageView ivLinkAnh_KhuVuc = (ImageView) convertView.findViewById(R.id.ivLinkAnh_KhuVuc);

        tvTen_KhuVuc.setText(khuvuc.TenKhuVuc);
        tvMota_KhuVuc.setText(khuvuc.MoTa + ".....");
        tvDanhgia_KhuVuc.setText(String.valueOf(khuvuc.DanhGia));
        tvSoLuotXem_KhuVuc.setText(String.valueOf(khuvuc.SoLuotXem));
        tvYeuThich_KhuVuc.setText(String.valueOf(khuvuc.YeuThich));
        new DownloadImageTask(ivLinkAnh_KhuVuc).execute(khuvuc.LinkAnh);

        return convertView;
    }


}

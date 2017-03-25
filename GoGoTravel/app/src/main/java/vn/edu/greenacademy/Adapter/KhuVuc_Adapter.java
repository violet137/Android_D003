package vn.edu.greenacademy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import vn.edu.greenacademy.Model.KhuVuc;
import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by GIT on 3/25/2017.
 */

public class KhuVuc_Adapter extends ArrayAdapter {

    Activity con;
    int layout;
    ArrayList<KhuVuc> arr;

    public KhuVuc_Adapter(Activity context, int resource, ArrayList<KhuVuc> objects) {

        super(context, resource, objects);

        this.con = context;
        this.layout = resource;
        this.arr = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = con.getLayoutInflater();
        convertView = inflater.inflate(layout, null);
        final KhuVuc khuvuc = arr.get(position);


        TextView tvTen_KhuVuc = (TextView) convertView.findViewById(R.id.tvTen_KhuVuc);
        TextView tvMota_KhuVuc = (TextView) convertView.findViewById(R.id.tvMoTa_KhuVuc);
        TextView tvDanhgia_KhuVuc = (TextView) convertView.findViewById(R.id.tvDanhGia_KhuVuc);
        TextView tvSoLuotXem_KhuVuc = (TextView) convertView.findViewById(R.id.tvSoLuotXem_KhuVuc);
        TextView tvYeuThich_KhuVuc = (TextView) convertView.findViewById(R.id.tvYeuThich_KhuVuc);
        ImageView ivLinkAnh_KhuVuc = (ImageView) convertView.findViewById(R.id.ivLinkAnh_KhuVuc);

        tvTen_KhuVuc.setText(khuvuc.TenKhuVuc);
        tvMota_KhuVuc.setText(khuvuc.MoTa);
        tvDanhgia_KhuVuc.setText(String.valueOf(khuvuc.DanhGia));
        tvSoLuotXem_KhuVuc.setText(String.valueOf(khuvuc.SoLuotXem));
        tvYeuThich_KhuVuc.setText(String.valueOf(khuvuc.YeuThich));
        new DownloadImageTask(ivLinkAnh_KhuVuc).execute(khuvuc.LinkAnh);

        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

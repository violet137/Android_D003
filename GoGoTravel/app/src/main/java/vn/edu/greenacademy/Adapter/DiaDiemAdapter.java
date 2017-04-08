package vn.edu.greenacademy.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

import vn.edu.greenacademy.Model.DiaDiem;
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

        ViewHolderDiaDiem holderDiaDiem;
        final DiaDiem diaDiem = arrDiaDiem.get(position);

        if (convertView == null){
            LayoutInflater inflater = con.getLayoutInflater();
            convertView = inflater.inflate(layout, null);

            holderDiaDiem = new ViewHolderDiaDiem();
            holderDiaDiem.tvTenDiaDiem = (TextView) convertView.findViewById(R.id.tvTenDiaDiem);
            holderDiaDiem.tvMotaDiaDiem = (TextView) convertView.findViewById(R.id.tvMoTaDiaDiem);
            holderDiaDiem.tvDanhgiaDiaDiem = (TextView) convertView.findViewById(R.id.tvDanhGiaDiaDiem);
            holderDiaDiem.tvSoLuotXemDiaDiem = (TextView) convertView.findViewById(R.id.tvSoLuotXemDiaDiem);
            holderDiaDiem.tvYeuThichDiaDiem = (TextView) convertView.findViewById(R.id.tvYeuThichDiaDiem);
            holderDiaDiem.ivLinkAnhDiaDiem = (ImageView) convertView.findViewById(R.id.ivLinkAnhDiaDiem);
            holderDiaDiem.tvCheckInDiaDiem = (TextView) convertView.findViewById(R.id.tvCheckInDiaDiem);
            holderDiaDiem.tvDiaChiDiaDiem = (TextView) convertView.findViewById(R.id.tvDiaChiDiaDiem);
            holderDiaDiem.mPosition = position;

            convertView.setTag(holderDiaDiem);
        }else {
            holderDiaDiem = (ViewHolderDiaDiem) convertView.getTag();
        }

        new DownloadImageTask(position,holderDiaDiem).execute(diaDiem.LinkAnh);
        holderDiaDiem.tvTenDiaDiem.setText(diaDiem.TenDiadiem);
        holderDiaDiem.tvMotaDiaDiem.setText(diaDiem.Mota);
        holderDiaDiem.tvDanhgiaDiaDiem.setText(String.valueOf(diaDiem.DanhGia));
        holderDiaDiem.tvSoLuotXemDiaDiem.setText(String.valueOf(diaDiem.SoLuotXem));
        holderDiaDiem.tvYeuThichDiaDiem.setText(String.valueOf(diaDiem.YeuThich));
        holderDiaDiem.tvCheckInDiaDiem.setText(String.valueOf(diaDiem.checkIn));
        holderDiaDiem.tvDiaChiDiaDiem.setText(diaDiem.Diachi);

        Picasso.with(getContext()).load(diaDiem.LinkAnh).resize(120,120).into(holderDiaDiem.ivLinkAnhDiaDiem);

        convertView.setBackgroundResource(R.drawable.border_dia_diem);

        return convertView;
    }
    private static class ViewHolderDiaDiem{
        public TextView tvTenDiaDiem;
        public TextView tvMotaDiaDiem;
        public TextView tvDanhgiaDiaDiem;
        public TextView tvSoLuotXemDiaDiem;
        public TextView tvYeuThichDiaDiem;
        public TextView tvCheckInDiaDiem;
        public TextView tvDiaChiDiaDiem;
        public int mPosition;
        public ImageView ivLinkAnhDiaDiem;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        public int mPosition;
        public ViewHolderDiaDiem mHolder;

        public DownloadImageTask(int mPosition, ViewHolderDiaDiem holder) {
            this.mPosition = mPosition;
            this.mHolder = holder;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                Bitmap.createScaledBitmap(mIcon11,120,120,true);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (mHolder.mPosition == mPosition){
                mHolder.ivLinkAnhDiaDiem.setImageBitmap(result);
            }
        }
    }



}

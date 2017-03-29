package vn.edu.greenacademy.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

        ViewHolderKhuVuc holder;
        final KhuVuc khuvuc = arrKhuVuc.get(position);


        if (convertView == null){
            LayoutInflater inflater = con.getLayoutInflater();
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolderKhuVuc();

            holder.tvTen_KhuVuc = (TextView) convertView.findViewById(R.id.tvTen_KhuVuc);
            holder.tvMota_KhuVuc = (TextView) convertView.findViewById(R.id.tvMoTa_KhuVuc);
            holder.tvDanhgia_KhuVuc = (TextView) convertView.findViewById(R.id.tvDanhGia_KhuVuc);
            holder.tvSoLuotXem_KhuVuc = (TextView) convertView.findViewById(R.id.tvSoLuotXem_KhuVuc);
            holder.tvYeuThich_KhuVuc = (TextView) convertView.findViewById(R.id.tvYeuThich_KhuVuc);
            holder.ivLinkAnh_KhuVuc = (ImageView) convertView.findViewById(R.id.ivLinkAnh_KhuVuc);
            holder.mPosition = position;
            convertView.setTag(holder);
        }else {
            holder = (ViewHolderKhuVuc) convertView.getTag();
        }
        holder.tvTen_KhuVuc.setText(khuvuc.TenKhuVuc);
        holder.tvMota_KhuVuc.setText(khuvuc.MoTa + ".....");
        holder.tvDanhgia_KhuVuc.setText(String.valueOf(khuvuc.DanhGia));
        holder.tvSoLuotXem_KhuVuc.setText(String.valueOf(khuvuc.SoLuotXem));
        holder.tvYeuThich_KhuVuc.setText(String.valueOf(khuvuc.YeuThich));
        new DownloadImageTask(position,holder).execute(khuvuc.LinkAnh);

        return convertView;
    }

    private static class ViewHolderKhuVuc{
        public TextView tvTen_KhuVuc;
        public TextView tvMota_KhuVuc;
        public TextView tvDanhgia_KhuVuc;
        public TextView tvSoLuotXem_KhuVuc;
        public TextView tvYeuThich_KhuVuc;
        public int mPosition;
        public ImageView ivLinkAnh_KhuVuc;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        public int mPosition;
        public ViewHolderKhuVuc mHolder;

        public DownloadImageTask(int mPosition, ViewHolderKhuVuc holder) {
            this.mPosition = mPosition;
            this.mHolder = holder;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                Bitmap.createScaledBitmap(mIcon11,360,300,true);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (mHolder.mPosition == mPosition){
                mHolder.ivLinkAnh_KhuVuc.setImageBitmap(result);
            }
        }
    }


}

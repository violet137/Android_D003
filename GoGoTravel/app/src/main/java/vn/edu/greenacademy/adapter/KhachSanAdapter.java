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
            view = mLayoutinlater.inflate(R.layout.fragment_khach_san,null);
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

        new Image(String.valueOf(khachSans.getLinkAnh()),imageKhachSan).execute();
        tvGiaTien.setText(String.valueOf(khachSans.getGia()));
        tvSoLuotCheckIn.setText(String.valueOf(khachSans.getCheckIn()));
        tvDanhGia.setText(String.valueOf(khachSans.getDanhGia()));
        tvSoLuotXem.setText(String.valueOf(khachSans.getSoLuotXem()));
        tvDiaChi.setText(khachSans.getMoTa());
        tvSoLuotYeuThich.setText(String.valueOf(khachSans.getYeuThich()));
        tvTenKhachSan.setText(khachSans.getTen());
        return view;
    }

    class Image extends AsyncTask<String, Void, Bitmap>{

        String strLinkAnh;
        ImageView ivImage;

        public Image(String strLinkAnh, ImageView ivImage) {
            this.strLinkAnh = strLinkAnh;
            this.ivImage = ivImage;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strLinkAnh);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ivImage.setImageBitmap(bitmap);
        }
    }
}

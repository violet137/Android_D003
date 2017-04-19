package vn.edu.greenacademy.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

import vn.edu.greenacademy.Model.DiaDiem;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailDiaDiemFragment extends Fragment {


    public DetailDiaDiemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_detail_dia_diem, container, false);
        ViewHolderDiaDiem holderDiaDiem;

            holderDiaDiem = new ViewHolderDiaDiem();
            holderDiaDiem.tvTenDiaDiem = (TextView) convertView.findViewById(R.id.tvDetailDiaDiem);
            holderDiaDiem.tvMotaDiaDiem = (TextView) convertView.findViewById(R.id.tvDetailMoTa);
            holderDiaDiem.tvSoLuotXemDiaDiem = (TextView) convertView.findViewById(R.id.tvDetaiSoLuotXem);
            holderDiaDiem.tvYeuThichDiaDiem = (TextView) convertView.findViewById(R.id.tvDetailYeuThich);
            holderDiaDiem.ivLinkAnhDiaDiem = (ImageView) convertView.findViewById(R.id.ivDetailLinkAnh);
            holderDiaDiem.tvCheckInDiaDiem = (TextView) convertView.findViewById(R.id.tvDetailCheckin);
            holderDiaDiem.tvDiaChiDiaDiem = (TextView) convertView.findViewById(R.id.tvDetaillDiaChi);


        final Bundle bundle = this.getArguments();
        if(bundle != null){
            DiaDiem diaDiem = (DiaDiem) bundle.getSerializable("DiaDiem");
            new DownloadImageTask(holderDiaDiem).execute(diaDiem.LinkAnh);
            holderDiaDiem.tvTenDiaDiem.setText(diaDiem.TenDiadiem);
            holderDiaDiem.tvMotaDiaDiem.setText("Mô tả  : " + diaDiem.Mota);
//            holderDiaDiem.tvDanhgiaDiaDiem.setText(String.valueOf(diaDiem.DanhGia));
            holderDiaDiem.tvSoLuotXemDiaDiem.setText(String.valueOf(diaDiem.SoLuotXem));
            holderDiaDiem.tvYeuThichDiaDiem.setText(String.valueOf(diaDiem.YeuThich));
            holderDiaDiem.tvCheckInDiaDiem.setText(String.valueOf(diaDiem.checkIn));
            holderDiaDiem.tvDiaChiDiaDiem.setText("Địa chỉ : " + diaDiem.Diachi);

            Picasso.with(getContext()).load(diaDiem.LinkAnh).into(holderDiaDiem.ivLinkAnhDiaDiem);
        }

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
        public ImageView ivLinkAnhDiaDiem;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        public ViewHolderDiaDiem mHolder;

        public DownloadImageTask(ViewHolderDiaDiem holder) {
            this.mHolder = holder;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//                Bitmap.createScaledBitmap(mIcon11,120,120,true);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            mHolder.ivLinkAnhDiaDiem.setImageBitmap(result);
        }
    }
}


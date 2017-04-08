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
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChitietQuanFragment extends Fragment {
    int id;
    ImageView ivHinh;
    TextView tvTen,tvDiachi,tvSoluot,tvMota,tvLike,tvCheckin;
    RatingBar rbDanhgia;

    private static ChitietQuanFragment instance;
    public static ChitietQuanFragment getInstance(){
        if(instance == null){
            instance = new ChitietQuanFragment();
        }

        return instance;
    }

    public ChitietQuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chitiet_quan, container, false);
        ivHinh = (ImageView) view.findViewById(R.id.ivHinhquan);
        tvTen = (TextView) view.findViewById(R.id.tvTenquan);
        tvDiachi = (TextView) view.findViewById(R.id.tvDiachi);
        tvSoluot = (TextView) view.findViewById(R.id.tvSoluot);
        tvMota = (TextView) view.findViewById(R.id.tvMota);
        tvLike = (TextView) view.findViewById(R.id.tvLike);
        tvCheckin = (TextView) view.findViewById(R.id.tvCheckin);
        rbDanhgia = (RatingBar) view.findViewById(R.id.rbDanhgia);

        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        String ten = bundle.getString("ten");
        String diachi = bundle.getString("diachi");
        String mota = bundle.getString("mota");
        float sao = bundle.getFloat("danhgia");
        int soluot = bundle.getInt("soluot");
        int like = bundle.getInt("like");
        int checkin = bundle.getInt("checkin");
        String link = bundle.getString("link");

        new DownloadImage(ivHinh).execute(link);
        tvTen.setText(ten);
        tvDiachi.setText("Địa chỉ : "+diachi);
        tvMota.setText("Mô tả : "+mota);
        rbDanhgia.setRating(sao);
        tvSoluot.setText("Views : "+String.valueOf(soluot));
        tvLike.setText("Like : "+String.valueOf(like));
        tvCheckin.setText("Checkin : "+String.valueOf(checkin));

        return view;
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView ivHinh) {
            this.imageView = ivHinh;
        }


        @Override
        protected Bitmap doInBackground(String... params) {
            String image_url = params[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(image_url);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }


}

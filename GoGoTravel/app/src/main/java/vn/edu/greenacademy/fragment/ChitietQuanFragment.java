package vn.edu.greenacademy.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import vn.edu.greenacademy.adapter.ImageAdapter;
import vn.edu.greenacademy.adapter.QuanNearAdapter;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.model.QuanAn;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChitietQuanFragment extends Fragment {
    int id;
    ImageView ivHinh;
    TextView tvTen,tvDiachi,tvSoluot,tvMota,tvLike,tvCheckin;
    RatingBar rbDanhgia;
    GridView gvHinh;

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
        gvHinh = (GridView) view.findViewById(R.id.gvHinh);

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

        new Danhsach_HinhAsyncTask().execute();

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

    public class Danhsach_HinhAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://103.237.147.137:9045/QuanAn/QuanAnByNear?lat=10&lng=10");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.addRequestProperty("Accept","text/json");
                connection.addRequestProperty("Content_type","application/json");
                connection.setRequestMethod("GET");

                connection.connect();

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader reader = new InputStreamReader(in);
                    BufferedReader buffer = new BufferedReader(reader);
                    String result = "";
                    String chunks ;
                    while ((chunks = buffer.readLine()) != null){
                        result += chunks;
                    }
                    return result;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                final List<QuanAn> list = new LinkedList<>();

                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");

                JSONArray listQuanNear = jsonObject.getJSONArray("QuanAns");
                for (int i = 0; i < 6; i++) {
                    JSONObject node = listQuanNear.getJSONObject(i);
                    String link = node.getString("LinkAnh");
                    String ten = node.getString("TenQuanAn");
                    String diachi = node.getString("DiaChi");
                    String mota = node.getString("MoTa");
                    float danhgia = node.getInt("DanhGia");
                    int soluot = node.getInt("SoLuotXem");
                    int yeuthich = node.getInt("YeuThich");
                    int checkin = node.getInt("CheckIn");
                    int id = node.getInt("Id");

                    if(status == 1){
                        QuanAn qa = new QuanAn();
                        qa.setLink(link);
                        qa.setTen(ten);
                        qa.setDiachi(diachi);
                        qa.setMota(mota);
                        qa.setDanhgia(danhgia);
                        qa.setSoluot(soluot);
                        qa.setYeuthich(yeuthich);
                        qa.setChenkin(checkin);
                        qa.setId(id);

                        list.add(qa);
                    }
                }
                //set adapter

                ImageAdapter imageAdapter = new ImageAdapter(getContext(),list);
                gvHinh.setAdapter(imageAdapter);
                imageAdapter.notifyDataSetChanged();



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

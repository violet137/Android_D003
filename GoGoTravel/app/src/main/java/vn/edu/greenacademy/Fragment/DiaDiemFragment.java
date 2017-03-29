package vn.edu.greenacademy.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import java.util.ArrayList;

import vn.edu.greenacademy.Adapter.DiaDiemAdapter;
import vn.edu.greenacademy.Model.DiaDiem;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaDiemFragment extends Fragment {

    DiaDiem diaDiem;
    ArrayList<DiaDiem> arrDiaDiem;
    DiaDiemAdapter diaDiemAdapter;
    ListView lvDiaDiem;

    public static DiaDiemFragment instance;

    public static DiaDiemFragment getInstance(){
        if (instance == null){
            instance = new DiaDiemFragment();
        }

        return instance;
    }

    public DiaDiemFragment() {
        // Required empty public constructor
    }

    //truyền dứ liệu giữa 2 fragment dùng Arguments


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_dia_diem, container, false);

        arrDiaDiem = new ArrayList<>();
        lvDiaDiem = (ListView) v.findViewById(R.id.lvDiaDiem);

        new getDiaDiem(v).execute(1);


//        Bundle bundle = this.getArguments();
//
//        if (bundle != null){
//            int i =  bundle.getInt("IdDiaDiem");
//            new getDiaDiem(v).execute(i);
//        }

        return v;
    }

    public class getDiaDiem extends AsyncTask<Integer, String, String> {

        View view;

        public getDiaDiem(View v) {
            this.view = v;
        }

        @Override
        protected String doInBackground(Integer... params) {
            try {
                URL url = new URL("http://103.237.147.137:9045/DiaDiem/DiaDiemById?idKhuVuc="+params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");

                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){

                    InputStream it = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);

                    String result = "";
                    String chenks;

                    while ((chenks = buff.readLine()) != null){
                        result += chenks;
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
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("DiaDiems");
                for (int i = 0; i < array.length(); i++) {
                    diaDiem = new DiaDiem();

                    JSONObject obj = array.getJSONObject(i);
                    diaDiem.Id = obj.getInt("Id");
                    diaDiem.TenDiadiem = obj.getString("TenDiaDiem");
                    diaDiem.Mota = obj.getString("MoTa");
                    diaDiem.DanhGia = obj.getDouble("DanhGia");
                    diaDiem.SoLuotXem = obj.getInt("SoLuotXem");
                    diaDiem.YeuThich = obj.getInt("YeuThich");
                    diaDiem.LinkAnh = obj.getString("LinkAnh");
                    diaDiem.Diachi = obj.getString("DiaChi");
                    diaDiem.IdKhuVuc = obj.getInt("IdKhuVuc");

                    arrDiaDiem.add(diaDiem);
                }
                diaDiemAdapter = new DiaDiemAdapter(getActivity(), R.layout.item_dia_diem, arrDiaDiem);
                lvDiaDiem.setAdapter(diaDiemAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

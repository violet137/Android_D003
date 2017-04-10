package vn.edu.greenacademy.gogotravel.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

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
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.edu.greenacademy.gogotravel.Adapter.ExpandableListView_HanhTrinh;
import vn.edu.greenacademy.gogotravel.Model.DiaDiemChuyenDiTranfers;
import vn.edu.greenacademy.gogotravel.Model.NgayChuyenDiTranfers;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HanhTrinhFragment extends Fragment {
    private View view;
    ExpandableListView elvHanhTrinh;
    ExpandableListView_HanhTrinh adapterHanhTrinh;
    ArrayList<NgayChuyenDiTranfers> arrlstNgayDi;
    NgayChuyenDiTranfers Ngaydi;
    DiaDiemChuyenDiTranfers Diadiem;
    public HanhTrinhFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_hanhtrinh, container, false);
        arrlstNgayDi = new ArrayList<>();

        new LoadHanhTrinhData().execute();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        elvHanhTrinh = (ExpandableListView) view.findViewById(R.id.elvHanhTrinh);

    }
    class LoadHanhTrinhData extends AsyncTask<String,String,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("NgayChuyenDiTranfers");
                for (int i =0; i < jsonArray.length();i++){
                    Ngaydi = new NgayChuyenDiTranfers();
                    //them data cho ngay di
                    JSONObject objectNgaydi = jsonArray.getJSONObject(i);
                    Ngaydi.idNgay = objectNgaydi.getInt("Id");
                    Ngaydi.NgayChuyenDi = objectNgaydi.getString("NgayChuyenDi");
                    Ngaydi.SoLuotLike = objectNgaydi.getInt("SoLuotLike");
                    Ngaydi.SoLuongAnh = objectNgaydi.getInt("SoLuongAnh");
                    //parse array cho diadiem
                    JSONArray jsonDiaDiem = objectNgaydi.getJSONArray("DiaDiemChuyenDiTranfers");
                    //them data cho item dia diem trong ngay di
                    for (int j = 0 ;j<jsonDiaDiem.length();j++){
                        Diadiem = new DiaDiemChuyenDiTranfers();
                        JSONObject objectDiaDiem = jsonDiaDiem.getJSONObject(j);
                        Diadiem.idChuyenDi = objectDiaDiem.getInt("Id");
                        Diadiem.IdNgayChuyenDi = objectDiaDiem.getInt("IdNgayChuyenDi");
                        Diadiem.IdDiaDiem = objectDiaDiem.getInt("IdDiaDiem");
                        Diadiem.LoaiDiaDiem = objectDiaDiem.getInt("LoaiDiaDiem");
                        Diadiem.NoiDungCheckIn = objectDiaDiem.getString("NoiDungCheckIn");
                        Diadiem.NgayCheckIn = objectDiaDiem.getString("NgayCheckIn");
                        Diadiem.SoLuotLike = objectDiaDiem.getInt("SoLuotLike");
                        Diadiem.SoLuongAnh = objectDiaDiem.getInt("SoLuongAnh");
                        Diadiem.LinkAnh = objectDiaDiem.getString("LinkAnh");
                        Diadiem.Address = objectDiaDiem.getString("Address");
                        Diadiem.TenDiaDiem = objectDiaDiem.getString("TenDiaDiem");
                        Ngaydi.arrDiaDiem.add(Diadiem);
                    }
                    arrlstNgayDi.add(Ngaydi);
                }
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");
                adapterHanhTrinh = new ExpandableListView_HanhTrinh(getContext(),arrlstNgayDi);
                elvHanhTrinh.setAdapter(adapterHanhTrinh);
                elvHanhTrinh.setGroupIndicator(null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://103.237.147.137:9045/MyTravel/MyTravel");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                    InputStream is = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(is);
                    BufferedReader buff = new BufferedReader(read);
                    String result = "";
                    String chunks;

                    while ((chunks = buff.readLine()) != null) {
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
    }
}

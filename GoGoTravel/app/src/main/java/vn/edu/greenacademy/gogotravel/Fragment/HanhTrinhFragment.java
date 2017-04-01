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
    HashMap<String,ArrayList<DiaDiemChuyenDiTranfers>> lstItems;
    NgayChuyenDiTranfers lstNgaydi;
    DiaDiemChuyenDiTranfers lstDiadiem;
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
        lstItems = new HashMap<>();

        new LoadHanhTrinhData().execute();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adapterHanhTrinh = new ExpandableListView_HanhTrinh(getContext(),arrlstNgayDi,lstItems);
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
                    lstNgaydi = new NgayChuyenDiTranfers();
                    //them data cho ngay di
                    JSONObject objectNgaydi = jsonArray.getJSONObject(i);
                    lstNgaydi.idNgay = objectNgaydi.getInt("Id");
                    lstNgaydi.NgayChuyenDi = objectNgaydi.getString("NgayChuyenDi");
                    lstNgaydi.SoLuotLike = objectNgaydi.getInt("SoLuotLike");
                    lstNgaydi.SoLuongAnh = objectNgaydi.getInt("SoLuongAnh");
                    //parse array cho diadiem
                    JSONArray jsonDiaDiem = objectNgaydi.getJSONArray("DiaDiemChuyenDiTranfers");
                    //them data cho item dia diem trong ngay di
                    for (int j = 0 ;j<jsonDiaDiem.length();j++){
                        lstDiadiem = new DiaDiemChuyenDiTranfers();
                        JSONObject objectDiaDiem = jsonDiaDiem.getJSONObject(j);
                        lstDiadiem.idChuyenDi = objectDiaDiem.getInt("Id");
                        lstDiadiem.IdNgayChuyenDi = objectDiaDiem.getInt("IdNgayChuyenDi");
                        lstDiadiem.IdDiaDiem = objectDiaDiem.getInt("IdDiaDiem");
                        lstDiadiem.LoaiDiaDiem = objectDiaDiem.getInt("LoaiDiaDiem");
                        lstDiadiem.NoiDungCheckIn = objectDiaDiem.getString("NoiDungCheckIn");
                        lstDiadiem.NgayCheckIn = objectDiaDiem.getString("NgayCheckIn");
                        lstDiadiem.SoLuotLike = objectDiaDiem.getInt("SoLuotLike");
                        lstDiadiem.SoLuongAnh = objectDiaDiem.getInt("SoLuongAnh");
                        lstDiadiem.LinkAnh = objectDiaDiem.getString("LinkAnh");
                        lstDiadiem.Address = objectDiaDiem.getString("Address");
                        lstDiadiem.TenDiaDiem = objectDiaDiem.getString("TenDiaDiem");
                        lstItems.put(String.valueOf(lstNgaydi.idNgay),lstNgaydi.arrDiaDiem);
                    }
                    arrlstNgayDi.add(lstNgaydi);
                }
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");
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

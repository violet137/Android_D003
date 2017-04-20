package vn.edu.greenacademy.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
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

import vn.edu.greenacademy.Model.DiaDiemMap;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.utils.Constant;




/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    private View view;
    private GoogleMap map;
    private int iID, iLuotXem, iYeuThich, iCheckIn;
    private String sTen, sMoTa, sLoaiMarker, sLinkAnh;
    private float fDanhGia;
    private double dLat, dLng;

    List<DiaDiemMap> arrDiaDiem = new LinkedList<>();

    Button btnTatCa, btnDiaDiem, btnQuanAn, btnKhachSan;

    public static MapFragment instance = null;

    public static  MapFragment getInstance(){
        if (instance == null){
            instance = new MapFragment();
        }
        return instance;
    }

    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public void onClick(View v) {
        List<DiaDiemMap> listTemp = new LinkedList<>();
        switch (v.getId())
        {
            case R.id.btnTatCa:
                listTemp=arrDiaDiem;
                break;
            case  R.id.btnDiaDiem:
                for (int i = 0; i<arrDiaDiem.size(); i++)
                {
                    if(arrDiaDiem.get(i).getsLoaiMarker().equals("DiaDiem"))
                    {
                        listTemp.add(arrDiaDiem.get(i));
                    }
                }
                break;
            case R.id.btnKhachSan:
                for (int i = 0; i<arrDiaDiem.size(); i++)
                {
                    if(arrDiaDiem.get(i).getsLoaiMarker().equals("KhachSan"))
                    {
                        listTemp.add(arrDiaDiem.get(i));
                    }
                }
                break;
            case R.id.btnQuanAn:
                for (int i = 0; i<arrDiaDiem.size(); i++) {
                    if (arrDiaDiem.get(i).getsLoaiMarker().equals("QuanAn")) {
                        listTemp.add(arrDiaDiem.get(i));
                    }
                }
                break;
        }
        dataStore(listTemp);
    }

    class AsyncDiaDiem extends AsyncTask<Double, String, String>
    {

        @Override
        protected String doInBackground(Double... params) {
            try {
                URL url = new URL("http://103.237.147.137:9045/DiaDiem/MapMarker?lat=" + params[0] + "&lng=" + params[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-type", "application/json");
                connection.setRequestMethod("GET");
                connection.connect();

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    InputStream it = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader reader = new InputStreamReader(it);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String result = "";
                    String chunks;
                    while((chunks=bufferedReader.readLine()) != null)
                    {
                        result+=chunks;
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
        protected void onPostExecute(String json) {
            try{
                JSONObject root=new JSONObject(json);
                JSONArray listDiaDiem = root.getJSONArray("MapMarkerTranfers");
                for(int i=0; i< listDiaDiem.length(); i++)
                {
                    JSONObject object = listDiaDiem.getJSONObject(i);
                    iID = object.getInt("Id");
                    sTen = object.getString("Ten");
                    sMoTa = object.getString("MoTa");
                    fDanhGia = object.getInt("DanhGia");
                    iLuotXem = object.getInt("SoLuotXem");
                    iYeuThich = object.getInt("YeuThich");
                    iCheckIn = object.getInt("CheckIn");
                    sLoaiMarker = object.getString("LoaiMarker");
                    sLinkAnh = object.getString("LinkAnh");
                    dLat = object.getDouble("Lat");
                    dLng = object.getDouble("Lng");

                    DiaDiemMap diaDiem = new DiaDiemMap();
                    diaDiem.setiID(iID);
                    diaDiem.setsTenDiaDiem(sTen);
                    diaDiem.setsMoTa(sMoTa);
                    diaDiem.setfDanhGia(fDanhGia);
                    diaDiem.setiLuotXem(iLuotXem);
                    diaDiem.setiYeuThich(iYeuThich);
                    diaDiem.setiCheckIn(iCheckIn);
                    diaDiem.setsLoaiMarker(sLoaiMarker);
                    diaDiem.setsLinkAnh(sLinkAnh);
                    diaDiem.setdLat(dLat);
                    diaDiem.setdLng(dLng);

                    arrDiaDiem.add(diaDiem);
                }

                dataStore(arrDiaDiem);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected void initView()
    {
        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);

        btnTatCa = (Button) view.findViewById(R.id.btnTatCa);
        btnDiaDiem = (Button) view.findViewById(R.id.btnDiaDiem);
        btnQuanAn = (Button) view.findViewById(R.id.btnQuanAn);
        btnKhachSan = (Button) view.findViewById(R.id.btnKhachSan);

        initView();
        btnTatCa.setOnClickListener(this);
        btnDiaDiem.setOnClickListener(this);
        btnKhachSan.setOnClickListener(this);
        btnQuanAn.setOnClickListener(this);

        return view;
    }

    public void dataStore(List<DiaDiemMap> list_temp)
    {
        map.clear();
        LatLng viTri=new LatLng(10,10);
        for(int i = 0; i<list_temp.size(); i++)
        {
            viTri = new LatLng(list_temp.get(i).getdLat(), list_temp.get(i).getdLng());
            switch(list_temp.get(i).getsLoaiMarker())
            {
                case "DiaDiem":
                    map.addMarker(new MarkerOptions().position(viTri).title("Địa Điểm")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_diadiem))).setTag(list_temp.get(i));
                    break;
                case "QuanAn":
                    map.addMarker(new MarkerOptions().position(viTri).title("Quán Ăn")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_quanan))).setTag(list_temp.get(i));
                    break;
                case "KhachSan":
                    map.addMarker(new MarkerOptions().position(viTri).title("Khách Sạn")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_khachsan))).setTag(list_temp.get(i));
                    break;
            }
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(viTri, Constant.MAP_ZOOM));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        new AsyncDiaDiem().execute(10d,10d);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        DiaDiemMap diaDiem = (DiaDiemMap) marker.getTag();
        //Toast.makeText(getContext(), diaDiem.getsTenDiaDiem(), Toast.LENGTH_LONG).show();
        showDialog(diaDiem);
        return true;
    }

    public void showDialog(DiaDiemMap diaDiem)
    {
        FragmentManager fm = getFragmentManager();
        CustomDialogFragment customDialogFragment = CustomDialogFragment.getInstance(diaDiem);
        customDialogFragment.show(fm, "");
    }

}

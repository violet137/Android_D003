package vn.edu.greenacademy.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.LinkedList;
import java.util.List;

import vn.edu.greenacademy.Adapter.KhachSanAdapter;
import vn.edu.greenacademy.Model.KhachSans;
import vn.edu.greenacademy.gogotravel.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class KhachSanFragment extends Fragment {

    Button btnBoLoc;
    ListView listViewKhachSan;
    KhachSanAdapter adapter;
    List<KhachSans> listHotel = new LinkedList<>();
    ArrayList arrChoice;
    int luaChon=0;
    static int id;

    public static KhachSanFragment instance;
    public static KhachSanFragment getInstance(){
        if (instance == null){
            instance = new KhachSanFragment();
        }
        return instance;
    }

    public static void setId(int ids) {
        KhachSanFragment.id = ids;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khach_san, container, false);

        btnBoLoc = (Button) view.findViewById(R.id.btnBoLoc);
        listViewKhachSan = (ListView) view.findViewById(R.id.listViewKhachSan);

//        new AllKhachSan().execute();
        new KhachSanByKhuVuc().execute();
//        btnBoLoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                arrChoice = new ArrayList();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle(R.string.title_dialog)
//                        .setSingleChoiceItems(R.array.danh_sach_bo_loc, 0, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                luaChon = which;
//                                switch (which) {
//                                    case 0:
//                                        break;
//                                    case 1:
//                                        Comparator comp = new Comparator<KhachSans>(){
//                                            @Override
//                                            public int compare(KhachSans s1, KhachSans s2)
//                                            {
//                                                if(s1.getDanhGia()>s2.getDanhGia()){
//                                                    return 1;
//                                                }else{
//                                                    return -1;
//                                                }
//                                            }
//                                        };
//                                        Collections.sort(listHotel, comp);
//                                        adapter.ReloadData(listHotel);
//                                        dialog.dismiss();
//                                        break;
//                                    case 2:
//                                        dialog.dismiss();
//                                        break;
//                                    case 3:
//                                        dialog.dismiss();
//                                        break;
//                                    case 4:
//                                        dialog.dismiss();
//                                        break;
//                                    case 5:
//                                        new KhachSanByNear().execute();
//                                        dialog.dismiss();
//                                        break;
//                                }
//
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
        listViewKhachSan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KhachSans tempKhachSan = listHotel.get(position);
                Fragment fragment = new DetailKhachSanFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("KhachSan",tempKhachSan);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(android.R.id.content, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    class AllKhachSan extends AsyncTask<List<KhachSans>, Void, String> {

        @Override
        protected String doInBackground(List<KhachSans>... lists) {
            try {
                URL url = new URL("http://103.237.147.137:9045/KhachSan/AllKhachSan");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET"); //config giao thức truyền lên server
                connection.connect();

                //nhận dữ liệu từ server về
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream it = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    String result = "";
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        // đọc tới khi nào server trả về null thì dừng
                        result += chunks;
                    }
                    return result;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //parse dữ liệu từ server về
            String responseString = s;
            JSONObject response = null;
            try {
                response = new JSONObject(responseString);
                int status = response.getInt("Status");
                String description = response.getString("Description");
                JSONArray lstHotel = response.getJSONArray("KhachSans");
                for (int i = 0; i < lstHotel.length(); i++) {
                    JSONObject node = lstHotel.getJSONObject(i);
                    int id = node.getInt("Id");
                    String ten = node.getString("Ten");
                    String mota = node.getString("MoTa");
                    double danhgia = node.getDouble("DanhGia");
                    int soluotxem = node.getInt("SoLuotXem");
                    int yeuthich = node.getInt("YeuThich");
                    int checkin = node.getInt("CheckIn");
                    int khuvucid = node.getInt("KhuVucId");
                    String linkanh = node.getString("LinkAnh");
                    double lat = node.getDouble("Lat");
                    double lng = node.getDouble("Lng");
                    String address = node.getString("Address");
                    double gia = node.getDouble("Gia");

                    KhachSans ksan = new KhachSans();
                    ksan.ID = id;
                    ksan.Ten = ten;
                    ksan.MoTa = mota;
                    ksan.DanhGia = danhgia;
                    ksan.SoLuotXem = soluotxem;
                    ksan.YeuThich = yeuthich;
                    ksan.CheckIn = checkin;
                    ksan.KhuVucID = khuvucid;
                    ksan.LinkAnh = linkanh;
                    ksan.Lat = lat;
                    ksan.Lng = lng;
                    ksan.Address = address;
                    ksan.Gia = gia;
                    listHotel.add(ksan);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new KhachSanAdapter(getContext(), R.layout.item_khachsan_layout, listHotel);
            listViewKhachSan.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    class KhachSanByKhuVuc extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://103.237.147.137:9045/KhachSan/KhachSanByKhuVuc?idKhuVuc=" + id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream it = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    String result = "";
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        result = result + chunks;
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
            String responseString = s;
            JSONObject response = null;
            try {
                response = new JSONObject(responseString);
                int status = response.getInt("Status");
                String description = response.getString("Description");
                JSONArray lstHotelByID = response.getJSONArray("KhachSans");
                for (int i = 0; i < lstHotelByID.length(); i++) {
                    JSONObject node = lstHotelByID.getJSONObject(i);
                    int id = node.getInt("Id");
                    String ten = node.getString("Ten");
                    String mota = node.getString("MoTa");
                    double danhgia = node.getDouble("DanhGia");
                    int soluotxem = node.getInt("SoLuotXem");
                    int yeuthich = node.getInt("YeuThich");
                    int checkin = node.getInt("CheckIn");
                    int khuvucid = node.getInt("KhuVucId");
                    String linkanh = node.getString("LinkAnh");
                    double lat = node.getDouble("Lat");
                    double lng = node.getDouble("Lng");
                    String address = node.getString("Address");
                    double gia = node.getDouble("Gia");

                    KhachSans ksanbyid = new KhachSans();
                    ksanbyid.ID = id;
                    ksanbyid.Ten = ten;
                    ksanbyid.MoTa = mota;
                    ksanbyid.DanhGia = danhgia;
                    ksanbyid.SoLuotXem = soluotxem;
                    ksanbyid.YeuThich = yeuthich;
                    ksanbyid.CheckIn = checkin;
                    ksanbyid.KhuVucID = khuvucid;
                    ksanbyid.LinkAnh = linkanh;
                    ksanbyid.Lat = lat;
                    ksanbyid.Lng = lng;
                    ksanbyid.Address = address;
                    ksanbyid.Gia = gia;
                    listHotel.add(ksanbyid);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new KhachSanAdapter(getContext(), R.layout.item_khachsan_layout, listHotel);
            listViewKhachSan.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    class KhachSanByNear extends AsyncTask<List<KhachSans>, Void, String> {

        @Override
        protected String doInBackground(List<KhachSans>... params) {
            try {
                URL url = new URL("http://103.237.147.137:9045/KhachSan/KhachSanByNear?lat=1&lng=1");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream it = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    String result = "";
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        result = result + chunks;
                    }
                    return result;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String responseString = s;
            JSONObject response = null;
            try {
                response = new JSONObject(responseString);
                int status = response.getInt("Status");
                String description = response.getString("Description");
                JSONArray lstHotelByID = response.getJSONArray("KhachSans");
                for (int i = 0; i < lstHotelByID.length(); i++) {
                    JSONObject node = lstHotelByID.getJSONObject(i);
                    int id = node.getInt("Id");
                    String ten = node.getString("Ten");
                    String mota = node.getString("MoTa");
                    double danhgia = node.getDouble("DanhGia");
                    int soluotxem = node.getInt("SoLuotXem");
                    int yeuthich = node.getInt("YeuThich");
                    int checkin = node.getInt("CheckIn");
                    int khuvucid = node.getInt("KhuVucId");
                    String linkanh = node.getString("LinkAnh");
                    double lat = node.getDouble("Lat");
                    double lng = node.getDouble("Lng");
                    String address = node.getString("Address");
                    double gia = node.getDouble("Gia");

                    KhachSans ksanbyid = new KhachSans();
                    ksanbyid.ID = id;
                    ksanbyid.Ten = ten;
                    ksanbyid.MoTa = mota;
                    ksanbyid.DanhGia = danhgia;
                    ksanbyid.SoLuotXem = soluotxem;
                    ksanbyid.YeuThich = yeuthich;
                    ksanbyid.CheckIn = checkin;
                    ksanbyid.KhuVucID = khuvucid;
                    ksanbyid.LinkAnh = linkanh;
                    ksanbyid.Lat = lat;
                    ksanbyid.Lng = lng;
                    ksanbyid.Address = address;
                    ksanbyid.Gia = gia;
                    listHotel.add(ksanbyid);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new KhachSanAdapter(getContext(), R.layout.item_khachsan_layout, listHotel);
            listViewKhachSan.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }


}

package vn.edu.greenacademy.fragment;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.LinkedList;
import java.util.List;

import vn.edu.greenacademy.adapter.KhachSanAdapter;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.model.KhachSans;

/**
 * A simple {@link Fragment} subclass.
 */
public class KhachSanFragment extends Fragment {

    Button btnBoLoc;
    ListView listViewKhachSan;
    KhachSanAdapter adapter;
    List<KhachSans> listHotel = new LinkedList<>();
    final CharSequence[] choice = {"Không lọc","Điểm","Số comment","Số lượt yêu thích","Mới nhất"};
    CharSequence myChoice;

    public KhachSanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_khach_san, container, false);

        btnBoLoc = (Button) view.findViewById(R.id.btnBoLoc);
        listViewKhachSan = (ListView) view.findViewById(R.id.listViewKhachSan);

        new AllHotel().execute();
        btnBoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Bạn muốn lọc theo: ")
                        .setSingleChoiceItems(choice, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch(i){
                                    case 0 :
                                        myChoice = choice[0];
                                        break;
                                    case 1 :
                                        myChoice = choice[1];
                                        break;
                                    case 2:
                                        myChoice = choice[2];
                                        break;
                                    case 3:
                                        myChoice = choice[3];
                                        break;
                                    case 4:
                                        myChoice = choice[4];
                                        break;
                                }
                            }
                        }).create();
            }
        });
        return view;
    }

    class AllHotel extends AsyncTask<List<KhachSans>, Void, String>{

        @Override
        protected String doInBackground(List<KhachSans>... lists) {
            try {
                URL url = new URL("http://103.237.147.137:9045/KhachSan/AllKhachSan");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept","text/json");
                connection.addRequestProperty("Content-Type","application/json");
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
                for(int i = 0; i<lstHotel.length(); i++){
                    JSONObject node = lstHotel.getJSONObject(i);
                    int id = node.getInt("Id");
                    String ten = node.getString("Ten");
                    String mota = node.getString("MoTa");
                    double danhgia = node.getDouble("DanhGia");
                    int soluotxem = node.getInt("oLuotXem");
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
            adapter = new KhachSanAdapter(getContext(),R.layout.item_khachsan_layout,listHotel);
            listViewKhachSan.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}

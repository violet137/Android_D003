package vn.edu.greenacademy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.LinkedList;
import java.util.List;

import vn.edu.greenacademy.adapter.LoaiQuanAdapter;
import vn.edu.greenacademy.adapter.QuanNearAdapter;
import vn.edu.greenacademy.adapter.QuanTopAdapter;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.model.LoaiQuan;
import vn.edu.greenacademy.model.QuanAn;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuanAnFragment extends Fragment {
    RecyclerView list1,list2;
    ListView list3;

    private static QuanAnFragment instance;
    public static QuanAnFragment getInstance(){
        if(instance == null){
            instance = new QuanAnFragment();
        }

        return instance;
    }


    public QuanAnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loaiquanan, container, false);

        list1 = (RecyclerView) view.findViewById(R.id.list1);
        list2 = (RecyclerView) view.findViewById(R.id.list2);
        list3 = (ListView) view.findViewById(R.id.list3);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        list1.setLayoutManager(layoutManager1);
        list1.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.HORIZONTAL));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        list2.setLayoutManager(layoutManager2);
        list2.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.HORIZONTAL));

        new LoaiquanAsynctask().execute();
        new QuanTopAsyncTask().execute();
        new QuanNearAsyncTask().execute();

        return view;
    }

    public class LoaiquanAsynctask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://103.237.147.137:9045/QuanAn/AllLoaiQuanAn");
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
                List<LoaiQuan> list = new LinkedList<>();

                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");
                JSONArray listLoaiquan = jsonObject.getJSONArray("LoaiQuanAns");
                for (int i = 0; i < listLoaiquan.length(); i++) {
                    JSONObject node = listLoaiquan.getJSONObject(i);
                    String link = node.getString("LinkAnh");
                    String ten = node.getString("TenLoaiQuanAn");
                    int sl = node.getInt("SoLuotXem");

                    if(status == 1){
                        LoaiQuan qa = new LoaiQuan();
                        qa.setLink(link);
                        qa.setTen(ten);
                        qa.setSoluot(sl);

                        list.add(qa);
                    }
                }
                //set adapter
                LoaiQuanAdapter loaiQuanAdapter=new LoaiQuanAdapter(list);
                list1.setAdapter(loaiQuanAdapter);
                loaiQuanAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public class QuanTopAsyncTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://103.237.147.137:9045/QuanAn/QuanAnByTop");
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
                    String chunks;
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
                List<QuanAn> list = new LinkedList<>();

                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");

                JSONArray listQuanTop = jsonObject.getJSONArray("QuanAns");
                for (int i = 0; i < listQuanTop.length(); i++) {
                    JSONObject node = listQuanTop.getJSONObject(i);
                    String link = node.getString("LinkAnh");
                    String ten = node.getString("TenQuanAn");
                    String mota = node.getString("MoTa");
                    int danhgia = node.getInt("DanhGia");
                    int yeuthich = node.getInt("YeuThich");
                    int checkin = node.getInt("CheckIn");

                    if(status == 1){
                        QuanAn qa = new QuanAn();
                        qa.setLink(link);
                        qa.setTen(ten);
                        qa.setMota(mota);
                        qa.setDanhgia(danhgia);
                        qa.setYeuthich(yeuthich);
                        qa.setChenkin(checkin);

                        list.add(qa);
                    }
                }
                //set adapter
                QuanTopAdapter quanTopAdapter = new QuanTopAdapter(list);
                list2.setAdapter(quanTopAdapter);
                quanTopAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class QuanNearAsyncTask extends AsyncTask<String, String, String>{

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
                List<QuanAn> list = new LinkedList<>();

                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");

                JSONArray listQuanTop = jsonObject.getJSONArray("QuanAns");
                for (int i = 0; i < listQuanTop.length(); i++) {
                    JSONObject node = listQuanTop.getJSONObject(i);
                    String link = node.getString("LinkAnh");
                    String ten = node.getString("TenQuanAn");
                    String mota = node.getString("MoTa");
                    int danhgia = node.getInt("DanhGia");
                    int yeuthich = node.getInt("YeuThich");
                    int checkin = node.getInt("CheckIn");

                    if(status == 1){
                        QuanAn qa = new QuanAn();
                        qa.setLink(link);
                        qa.setTen(ten);
                        qa.setMota(mota);
                        qa.setDanhgia(danhgia);
                        qa.setYeuthich(yeuthich);
                        qa.setChenkin(checkin);

                        list.add(qa);
                    }
                }
                //set adapter
                QuanNearAdapter quanNearAdapter = new QuanNearAdapter(getActivity(),R.layout.item_quanan,list);
                list3.setAdapter(quanNearAdapter);
                quanNearAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}

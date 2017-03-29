package vn.edu.greenacademy.Fragment;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import vn.edu.greenacademy.Adapter.KhuVuc_Adapter;
import vn.edu.greenacademy.Model.KhuVuc;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KhuVucFragment extends Fragment {

    Activity activity;
    KhuVuc khuVuc;
    ArrayList<KhuVuc> arrKhuVuc;
    KhuVuc_Adapter khuVuc_adapter;
    ListView lvKhuVuc;




    public static KhuVucFragment instance;

    public static KhuVucFragment getInstance(){
        if (instance == null){
            instance = new KhuVucFragment();
        }

        return instance;
    }

    public KhuVucFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_khu_vuc, container, false);
        lvKhuVuc = (ListView) v.findViewById(R.id.lvkhuVuc);
        arrKhuVuc = new ArrayList<>();


        new getKhuVuc(v).execute();

        lvKhuVuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KhuVuc khuVuc = arrKhuVuc.get(position);
                Fragment detail = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("KhuVuc",khuVuc.Id);
                detail.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flKhuVuc,detail);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return v;
    }



    public class getKhuVuc extends AsyncTask<Void, String, String> {

        View view;

        public getKhuVuc(View v) {
            this.view = v;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://103.237.147.137:9045/KhuVuc/AllKhuVuc");
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
                JSONArray array = jsonObject.getJSONArray("KhuVucs");
                for (int i = 0; i < array.length(); i++) {
                    khuVuc = new KhuVuc();

                    JSONObject obj = array.getJSONObject(i);
                    khuVuc.Id = obj.getInt("Id");
                    khuVuc.TenKhuVuc = obj.getString("TenKhuVuc");
                    khuVuc.MoTa = obj.getString("MoTa");
                    khuVuc.DanhGia = obj.getDouble("DanhGia");
                    khuVuc.SoLuotXem = obj.getInt("SoLuotXem");
                    khuVuc.YeuThich = obj.getInt("YeuThich");
                    khuVuc.LinkAnh = obj.getString("LinkAnh");

                    arrKhuVuc.add(khuVuc);
                }
//                int status = jsonObject.getInt("Status");
//                String description = jsonObject.getString("Description");
                khuVuc_adapter = new KhuVuc_Adapter(getActivity(), R.layout.item_khu_vuc, arrKhuVuc);
                lvKhuVuc.setAdapter(khuVuc_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

package vn.edu.greenacademy.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.util.ArrayList;

import vn.edu.greenacademy.Adapter.KhuVuc_Adapter;
import vn.edu.greenacademy.Model.KhuVuc;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class KhuVucFragment extends Fragment  {


    KhuVuc khuVuc;
    ArrayList<KhuVuc> arrKhuVuc;
    KhuVuc_Adapter khuVuc_adapter;
    ListView lvKhuVuc;
    View v;
    int checkReload = 1;

    DetailFragment detailFragment;

    FrameLayout flKhuVuc;

    HttpURLConnection con;
    InputStream it;
    InputStreamReader read;
    BufferedReader buff;


    getKhuVuc dataKhuVuc;

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
        v = inflater.inflate(R.layout.fragment_khu_vuc, container, false);

        lvKhuVuc = (ListView) v.findViewById(R.id.lvkhuVuc);

        flKhuVuc = (FrameLayout) v.findViewById(R.id.flKhuVuc);

        detailFragment = DetailFragment.getInstance();

        arrKhuVuc = new ArrayList<>();
        dataKhuVuc = new getKhuVuc(v);
        dataKhuVuc.execute();
        lvKhuVuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KhuVuc khuVuc = arrKhuVuc.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("khuvuc",khuVuc.Id);
                Constant.SET_ID = khuVuc.Id;
                detailFragment.setArguments(bundle);
                if (checkReload > 1){
                    detailFragment.onResume();
                }
                callFragment(detailFragment);
            }
        });
        return v;
    }


    public void callFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
                con = (HttpURLConnection) url.openConnection();
                con.addRequestProperty("Accept", "text/json");
                con.addRequestProperty("Content-Type", "application/json");
                con.setRequestMethod("GET");
                con.connect();
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
                     it = new BufferedInputStream(con.getInputStream());
                     read = new InputStreamReader(it);
                     buff = new BufferedReader(read);
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
            }finally {
                closeConnect();
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
                    if (isCancelled())break;
                }
                khuVuc_adapter = new KhuVuc_Adapter(getActivity(), R.layout.item_khu_vuc, arrKhuVuc);
                lvKhuVuc.setAdapter(khuVuc_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void closeConnect(){
        try {
            if (buff != null){
                buff.close();
            }
            if (read != null){
                read.close();
            }
            if (it != null){
                it.close();
            }
            if (con != null){
                con.disconnect();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

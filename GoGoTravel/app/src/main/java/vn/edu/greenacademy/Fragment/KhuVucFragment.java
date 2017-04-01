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
public class KhuVucFragment extends Fragment implements View.OnClickListener {


    KhuVuc khuVuc;
    ArrayList<KhuVuc> arrKhuVuc;
    KhuVuc_Adapter khuVuc_adapter;
    ListView lvKhuVuc;
    View v;

    KhuVucFragment khuVucFragment;
    BanDoFragment banDoFragment;
    TaiKhoanFragment taiKhoanFragment;
    HanhTrinhFragment hanhTrinhFragment;
    DetailFragment detailFragment;

    ImageButton ibHome, ibBanDo, ibhanhTrinh, ibTaiKhoan;
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
        ibHome = (ImageButton) v.findViewById(R.id.ibHome);
        ibBanDo = (ImageButton) v.findViewById(R.id.ibBanDo);
        ibhanhTrinh = (ImageButton) v.findViewById(R.id.ibhanhTrinh);
        ibTaiKhoan = (ImageButton) v.findViewById(R.id.ibTaiKhoan);
        flKhuVuc = (FrameLayout) v.findViewById(R.id.flKhuVuc);

        khuVucFragment = KhuVucFragment.getInstance();
        banDoFragment = BanDoFragment.getInstance();
        taiKhoanFragment = TaiKhoanFragment.getInstance();
        hanhTrinhFragment = HanhTrinhFragment.getInstance();
        detailFragment = DetailFragment.getInstance();

        ibHome.setOnClickListener(this);
        ibBanDo.setOnClickListener(this);
        ibhanhTrinh.setOnClickListener(this);
        ibTaiKhoan.setOnClickListener(this);
        arrKhuVuc = new ArrayList<>();

        lvKhuVuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KhuVuc khuVuc = arrKhuVuc.get(position);
                Fragment detail = DetailFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putInt("khuvuc",khuVuc.Id);
                detail.setArguments(bundle);
                callFragment(detail);
                dataKhuVuc.cancel(true);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataKhuVuc = new getKhuVuc(v);
        dataKhuVuc.execute();
    }

    public void callFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.flKhuVuc, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibHome:
                callFragment(khuVucFragment);
                break;
            case R.id.ibBanDo:
                callFragment(banDoFragment);
                break;
            case R.id.ibhanhTrinh:
                callFragment(hanhTrinhFragment);
                break;
            case R.id.ibTaiKhoan:
                callFragment(taiKhoanFragment);
                break;
        }
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
//                int status = jsonObject.getInt("Status");
//                String description = jsonObject.getString("Description");
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

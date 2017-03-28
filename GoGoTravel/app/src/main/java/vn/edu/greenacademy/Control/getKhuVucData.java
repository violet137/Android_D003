package vn.edu.greenacademy.Control;

import android.app.Activity;
import android.os.AsyncTask;
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
import vn.edu.greenacademy.gogotravel.MainActivity;
import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by GIT on 3/28/2017.
 */

public class getKhuVucData {

    Activity activity;
    KhuVuc khuVuc;
    ArrayList<KhuVuc> arrKhuVuc;
    KhuVuc_Adapter khuVuc_adapter;
    ListView lvKhuVuc;

    public class getKhuVuc extends AsyncTask<Void, String, String> {
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
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");


                khuVuc_adapter = new KhuVuc_Adapter(activity, R.layout.item_khu_vuc, arrKhuVuc);
                lvKhuVuc.setAdapter(khuVuc_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

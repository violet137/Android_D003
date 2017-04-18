package vn.edu.greenacademy.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.LinkedList;
import java.util.List;

import vn.edu.greenacademy.Adapter.QuanNearAdapter;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.Model.QuanAn;
import vn.edu.greenacademy.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class DanhsachQuan_LoaiFragment extends Fragment {
    ListView listloai;

    private static DanhsachQuan_LoaiFragment instance;
    public static DanhsachQuan_LoaiFragment getInstance(){
        if(instance == null){
            instance = new DanhsachQuan_LoaiFragment();
        }

        return instance;
    }


    public DanhsachQuan_LoaiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_danhsach_quan__loai, container, false);
        listloai = (ListView)view.findViewById(R.id.list_loai);

        new Danhsach_LoaiAsyncTask().execute();


        return view;
    }

    public class Danhsach_LoaiAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
//
                URL url = new URL("http://103.237.147.137:9045/QuanAn/QuanAnByType?idLoaiQuan="+ Constant.SET_ID);
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
                final List<QuanAn> list = new LinkedList<>();

                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");

                JSONArray listQuanNear = jsonObject.getJSONArray("QuanAns");
                for (int i = 0; i < listQuanNear.length(); i++) {
                    JSONObject node = listQuanNear.getJSONObject(i);
                    String link = node.getString("LinkAnh");
                    String ten = node.getString("TenQuanAn");
                    String diachi = node.getString("DiaChi");
                    String mota = node.getString("MoTa");
                    float danhgia = node.getInt("DanhGia");
                    int soluot = node.getInt("SoLuotXem");
                    int yeuthich = node.getInt("YeuThich");
                    int checkin = node.getInt("CheckIn");
                    int id = node.getInt("Id");

                    if(status == 1){
                        QuanAn qa = new QuanAn();
                        qa.setLink(link);
                        qa.setTen(ten);
                        qa.setDiachi(diachi);
                        qa.setMota(mota);
                        qa.setDanhgia(danhgia);
                        qa.setSoluot(soluot);
                        qa.setYeuthich(yeuthich);
                        qa.setChenkin(checkin);
                        qa.setId(id);

                        list.add(qa);
                    }
                }
                //set adapter
                QuanNearAdapter quanNearAdapter = new QuanNearAdapter(getActivity(),R.layout.item_quanan,list);
                listloai.setAdapter(quanNearAdapter);
                quanNearAdapter.notifyDataSetChanged();

                quanNearAdapter.onItemClickListener(new QuanNearAdapter.ItemClickListener(){
                    @Override
                    public void ItemClick(int position) {
                        Fragment fragment = new ChitietQuanFragment();

                        Bundle bundle = new Bundle();
                        bundle.putInt("id",list.get(position).getId());
                        bundle.putString("ten",list.get(position).getTen());
                        bundle.putString("diachi",list.get(position).getDiachi());
                        bundle.putString("mota",list.get(position).getMota());
                        bundle.putFloat("danhgia",list.get(position).getDanhgia());
                        bundle.putInt("soluot",list.get(position).getSoluot());
                        bundle.putInt("like",list.get(position).getYeuthich());
                        bundle.putInt("checkin",list.get(position).getChenkin());
                        bundle.putString("link",list.get(position).getLink());
                        fragment.setArguments(bundle);
                        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.flChiTietKhuVuc,fragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    }
                });


                listloai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Fragment fragment = new ChitietQuanFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",list.get(i).getId());
                        bundle.putString("ten",list.get(i).getTen());
                        bundle.putString("diachi",list.get(i).getDiachi());
                        bundle.putString("mota",list.get(i).getMota());
                        bundle.putFloat("danhgia",list.get(i).getDanhgia());
                        bundle.putInt("soluot",list.get(i).getSoluot());
                        bundle.putInt("like",list.get(i).getYeuthich());
                        bundle.putInt("checkin",list.get(i).getChenkin());
                        bundle.putString("link",list.get(i).getLink());
                        fragment.setArguments(bundle);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

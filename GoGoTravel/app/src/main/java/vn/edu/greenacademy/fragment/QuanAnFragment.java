package vn.edu.greenacademy.Fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

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

import vn.edu.greenacademy.Adapter.LoaiQuanAdapter;
import vn.edu.greenacademy.Adapter.QuanNearAdapter;
import vn.edu.greenacademy.Adapter.QuanTopAdapter;
import vn.edu.greenacademy.Model.LoaiQuan;
import vn.edu.greenacademy.Model.QuanAn;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuanAnFragment extends Fragment {
    RecyclerView list1,list2;
    ListView list3;
    ScrollView scrollView;
    Button btnAll;

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
        btnAll = (Button) view.findViewById(R.id.btnAll);
        scrollView = (ScrollView) view.findViewById(R.id.sv);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        list1.setLayoutManager(layoutManager1);
        list1.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.HORIZONTAL));
        list1.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        list2.setLayoutManager(layoutManager2);
        list2.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.HORIZONTAL));
        list2.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        list3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });

        new LoaiquanAsynctask().execute();
        new QuanTopAsyncTask().execute();
        new QuanNearAsyncTask().execute();

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout,new DanhsachQuan_AllFragment(),"DanhsachQuan_AllFragment").commit();
                Fragment fragment = new DanhsachQuan_AllFragment();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout,fragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return view;
    }

    public class LoaiquanAsynctask extends AsyncTask<String, String, String> {

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
                final List<LoaiQuan> list = new LinkedList<>();

                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");
                JSONArray listLoaiquan = jsonObject.getJSONArray("LoaiQuanAns");
                for (int i = 0; i < listLoaiquan.length(); i++) {
                    JSONObject node = listLoaiquan.getJSONObject(i);
                    String link = node.getString("LinkAnh");
                    String ten = node.getString("TenLoaiQuanAn");
                    int sl = node.getInt("SoLuotXem");
                    int id =node.getInt("Id");

                    if(status == 1){
                        LoaiQuan qa = new LoaiQuan();
                        qa.setLink(link);
                        qa.setTen(ten);
                        qa.setSoluot(sl);
                        qa.setId(id);

                        list.add(qa);
                    }
                }
                //set adapter


                LoaiQuanAdapter loaiQuanAdapter=new LoaiQuanAdapter(list);
                list1.setAdapter(loaiQuanAdapter);
                loaiQuanAdapter.notifyDataSetChanged();



                loaiQuanAdapter.onItemClickListener(new LoaiQuanAdapter.ItemClickListener() {
                    @Override
                    public void ItemClick(View view, int position) {
                        Fragment fragment = new DanhsachQuan_LoaiFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",list.get(position).getId());
                        fragment.setArguments(bundle);
                        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.layout,fragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    }
                });

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
                final List<QuanAn> listqt = new LinkedList<>();

                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");

                JSONArray listQuanTop = jsonObject.getJSONArray("QuanAns");
                for (int i = 0; i < listQuanTop.length(); i++) {
                    JSONObject node = listQuanTop.getJSONObject(i);
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

                        listqt.add(qa);
                    }
                }
                //set adapter

                QuanTopAdapter quanTopAdapter = new QuanTopAdapter(listqt);
                list2.setAdapter(quanTopAdapter);
                quanTopAdapter.notifyDataSetChanged();

                quanTopAdapter.onItemClickListener(new QuanTopAdapter.ItemClickListener() {
                    @Override
                    public void ItemClick(View view, int position) {
                        Fragment fragment = new ChitietQuanFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",listqt.get(position).getId());
                        bundle.putString("ten",listqt.get(position).getTen());
                        bundle.putString("diachi",listqt.get(position).getDiachi());
                        bundle.putString("mota",listqt.get(position).getMota());
                        bundle.putFloat("danhgia",listqt.get(position).getDanhgia());
                        bundle.putInt("soluot",listqt.get(position).getSoluot());
                        bundle.putInt("like",listqt.get(position).getYeuthich());
                        bundle.putInt("checkin",listqt.get(position).getChenkin());
                        bundle.putString("link",listqt.get(position).getLink());
                        fragment.setArguments(bundle);
                        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.layout,fragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    }
                });


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
                final List<QuanAn> listqn = new LinkedList<>();

                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");

                JSONArray listQuanNear = jsonObject.getJSONArray("QuanAns");
                for (int i = 0; i < 5; i++) {
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

                        listqn.add(qa);
                    }
                }
                //set adapter
                TextView header = new TextView(getContext());
                header.setText("Quán gần đây");
<<<<<<< HEAD
                header.setTypeface(null, Typeface.BOLD_ITALIC);
                header.setTextColor(getResources().getColor(R.color.colorAccent));
=======
>>>>>>> db0f9e3a9876a82ce16386079249f69d7838286f
                list3.addHeaderView(header);
                QuanNearAdapter quanNearAdapter = new QuanNearAdapter(getActivity(),R.layout.item_quanan,listqn);
                list3.setAdapter(quanNearAdapter);
                quanNearAdapter.notifyDataSetChanged();

                quanNearAdapter.onItemClickListener(new QuanNearAdapter.ItemClickListener(){
                    @Override
                    public void ItemClick(int position) {
                        Fragment fragment = new ChitietQuanFragment();

                        Bundle bundle = new Bundle();
                        bundle.putInt("id",listqn.get(position).getId());
                        bundle.putString("ten",listqn.get(position).getTen());
                        bundle.putString("diachi",listqn.get(position).getDiachi());
                        bundle.putString("mota",listqn.get(position).getMota());
                        bundle.putFloat("danhgia",listqn.get(position).getDanhgia());
                        bundle.putInt("soluot",listqn.get(position).getSoluot());
                        bundle.putInt("like",listqn.get(position).getYeuthich());
                        bundle.putInt("checkin",listqn.get(position).getChenkin());
                        bundle.putString("link",listqn.get(position).getLink());
                        fragment.setArguments(bundle);
                        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.layout,fragment);
                        transaction.addToBackStack(null);

                        transaction.commit();
                    }
                });

                list3.setItemsCanFocus(false);

                list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    TextView tvDemoQuanAn;
//    static int id = 1;
//
//    public static QuanAnFragment instance;
//
//    public static QuanAnFragment getInstance(){
//        if (instance == null){
//            instance = new QuanAnFragment();
//        }
//
//        return instance;
//    }
//
//    public QuanAnFragment() {
//    }
//
//
//    public static void setId(int id) {
//        QuanAnFragment.id = id;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_quan_an, container, false);
//
//        tvDemoQuanAn = (TextView) v.findViewById(R.id.tvDemoQuanAn);
//
//        tvDemoQuanAn.setText(id+"");
//
//        return v;
//    }

}

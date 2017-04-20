package vn.edu.greenacademy.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import vn.edu.greenacademy.Adapter.ListView_TimDiaDiem;
import vn.edu.greenacademy.Model.DiaDiemChuyenDiTranfers;
import vn.edu.greenacademy.Model.NgayChuyenDiTranfers;
import vn.edu.greenacademy.Model.TimDiemTranfers;
import vn.edu.greenacademy.Adapter.ExpandableListView_HanhTrinh;
import vn.edu.greenacademy.gogotravel.ChiTietCheckInActivity;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HanhTrinhFragment extends Fragment {
    private View view;
    ExpandableListView elvHanhTrinh;
    ListView lstSearchDiaDiem;
    ExpandableListView_HanhTrinh adapterHanhTrinh;
    ListView_TimDiaDiem adapterTimDiaDiem;
    ArrayList<NgayChuyenDiTranfers> arrlstNgayDi;
    ArrayList<TimDiemTranfers> arrlstTimDiem;
    NgayChuyenDiTranfers Ngaydi;
    TimDiemTranfers TimDiem;
    DiaDiemChuyenDiTranfers Diadiem;
    FloatingActionButton btnAdd;
    Dialog dialogSearch, dialogAdd;

    public static HanhTrinhFragment instance;

    public static HanhTrinhFragment getInstance(){
        if (instance == null){
            instance = new HanhTrinhFragment();
        }
        return instance;
    }

    public HanhTrinhFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hanh_trinh, container, false);
        arrlstNgayDi = new ArrayList<>();
        arrlstTimDiem = new ArrayList<>();
        new LoadHanhTrinhData().execute();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        elvHanhTrinh = (ExpandableListView) view.findViewById(R.id.elvHanhTrinh);
        btnAdd = (FloatingActionButton) view.findViewById(R.id.btnAdd);

        elvHanhTrinh.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String tendiadiem = arrlstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getTenDiaDiem();
                String thoigian = arrlstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getNgayCheckIn();
                String linkanh = arrlstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getLinkAnh();
                String noidung = arrlstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getNoiDungCheckIn();


                Intent intent = new Intent(getContext(), ChiTietCheckInActivity.class);
                intent.putExtra("tendiadiem",tendiadiem);
                intent.putExtra("thoigian",thoigian);
                intent.putExtra("linkanh",linkanh);
                intent.putExtra("noidung",noidung);
                startActivity(intent);

                return false;
            }
        });

        //Floating button add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog tim
                dialogSearch = new Dialog(getContext());
                dialogSearch.setContentView(R.layout.dialog_tim_diadiem);
                dialogSearch.setTitle("Thêm địa điểm");

                final EditText etSearchDiaDiem = (EditText) dialogSearch.findViewById(R.id.etSearchDiadiem);
                Button btnSearch = (Button) dialogSearch.findViewById(R.id.btnSearchDiadiem);
                lstSearchDiaDiem = (ListView) dialogSearch.findViewById(R.id.lstTimDiaDiem);

                lstSearchDiaDiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                        dialogAdd = new Dialog(getContext());
                        dialogAdd.setContentView(R.layout.dialog_them_diadiem);

                        TextView tvTenDiaDiemAdd = (TextView) dialogAdd.findViewById(R.id.tvTenDiaDiemAdd);
                        final EditText etNoiDungCheckin = (EditText) dialogAdd.findViewById(R.id.etNoiDungCheckin);
                        Button btnThem = (Button) dialogAdd.findViewById(R.id.btnDialogAdd);
                        Button btnChupAnh = (Button) dialogAdd.findViewById(R.id.btnCapture);
                        GridView gvCheckIn = (GridView) dialogAdd.findViewById(R.id.gvCheckIn);

                        tvTenDiaDiemAdd.setText(arrlstTimDiem.get(position).getTen());

                        btnThem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String idDiaDiem = String.valueOf(arrlstTimDiem.get(position).getId());
                                String LoaiDiaDiem = String.valueOf( arrlstTimDiem.get(position).getLoaiDiem());
                                String NoiDungcheckin = etNoiDungCheckin.getText().toString();

                                new ThemDiaDiem().execute(idDiaDiem,LoaiDiaDiem,NoiDungcheckin);
                            }
                        });
                        btnChupAnh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                man hinh chup anh
                            }
                        });

                        dialogAdd.show();
                    }
                });
                //button tim trong dialog
                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new GetDiaDiem().execute(etSearchDiaDiem.getText().toString());
                    }
                });

                dialogSearch.show();

            }
        });

    }

    class LoadHanhTrinhData extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("NgayChuyenDiTranfers");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Ngaydi = new NgayChuyenDiTranfers();
                    //them data cho ngay di
                    JSONObject objectNgaydi = jsonArray.getJSONObject(i);
                    Ngaydi.idNgay = objectNgaydi.getInt("Id");
                    Ngaydi.NgayChuyenDi = objectNgaydi.getString("NgayChuyenDi");
                    Ngaydi.SoLuotLike = objectNgaydi.getInt("SoLuotLike");
                    Ngaydi.SoLuongAnh = objectNgaydi.getInt("SoLuongAnh");
                    //parse array cho diadiem
                    JSONArray jsonDiaDiem = objectNgaydi.getJSONArray("DiaDiemChuyenDiTranfers");
                    //them data cho item dia diem trong ngay di
                    for (int j = 0; j < jsonDiaDiem.length(); j++) {
                        Diadiem = new DiaDiemChuyenDiTranfers();
                        JSONObject objectDiaDiem = jsonDiaDiem.getJSONObject(j);
                        Diadiem.idChuyenDi = objectDiaDiem.getInt("Id");
                        Diadiem.IdNgayChuyenDi = objectDiaDiem.getInt("IdNgayChuyenDi");
                        Diadiem.IdDiaDiem = objectDiaDiem.getInt("IdDiaDiem");
                        Diadiem.LoaiDiaDiem = objectDiaDiem.getInt("LoaiDiaDiem");
                        Diadiem.NoiDungCheckIn = objectDiaDiem.getString("NoiDungCheckIn");
                        Diadiem.NgayCheckIn = objectDiaDiem.getString("NgayCheckIn");
                        Diadiem.SoLuotLike = objectDiaDiem.getInt("SoLuotLike");
                        Diadiem.SoLuongAnh = objectDiaDiem.getInt("SoLuongAnh");
                        Diadiem.LinkAnh = objectDiaDiem.getString("LinkAnh");
                        Diadiem.Address = objectDiaDiem.getString("Address");
                        Diadiem.TenDiaDiem = objectDiaDiem.getString("TenDiaDiem");
                        Ngaydi.arrDiaDiem.add(Diadiem);
                    }
                    arrlstNgayDi.add(Ngaydi);
                }
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");
                adapterHanhTrinh = new ExpandableListView_HanhTrinh(getContext(), arrlstNgayDi);
                elvHanhTrinh.setAdapter(adapterHanhTrinh);
                elvHanhTrinh.setGroupIndicator(null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://103.237.147.137:9045/MyTravel/MyTravel");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(is);
                    BufferedReader buff = new BufferedReader(read);
                    String result = "";
                    String chunks;

                    while ((chunks = buff.readLine()) != null) {
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
    }

    class GetDiaDiem extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray arrTimDiem = jsonObject.getJSONArray("TimDiemTranfers");

                for (int i = 0; i < arrTimDiem.length(); i++) {
                    TimDiem = new TimDiemTranfers();
                    JSONObject TimdiemObject = arrTimDiem.getJSONObject(i);

                    TimDiem.Id = TimdiemObject.getInt("Id");
                    TimDiem.LoaiDiem = TimdiemObject.getInt("LoaiDiem");
                    TimDiem.Ten = TimdiemObject.getString("Ten");
                    TimDiem.Mota = TimdiemObject.getString("Mota");
                    TimDiem.Hinh = TimdiemObject.getString("Hinh");
                    TimDiem.Like = TimdiemObject.getInt("Like");
                    TimDiem.DanhGiaSao = TimdiemObject.getInt("DangGiaSao");
                    TimDiem.DiaChi = TimdiemObject.getString("DiaChi");

                    arrlstTimDiem.add(TimDiem);
                }
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");

                adapterTimDiaDiem = new ListView_TimDiaDiem(getContext(), arrlstTimDiem);
                lstSearchDiaDiem.setAdapter(adapterTimDiaDiem);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL("http://103.237.147.137:9045/MyTravel/SearchDiaDiem?timDiaDiem=" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(is);
                    BufferedReader buff = new BufferedReader(read);
                    String result = "";
                    String chunks;

                    while ((chunks = buff.readLine()) != null) {
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
    }

    class ThemDiaDiem extends AsyncTask<String, String, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(getContext(),"Thêm Thành Công !", Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonObject = new JSONObject(s);
                int status = jsonObject.getInt("Status");
                String description = jsonObject.getString("Description");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL("http://103.237.147.137:9045/MyTravel/ThemDiem");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Accept", "text/json");
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("POST");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("IdDiaDiem",Integer.parseInt(params[0]));
                jsonObject.put("LoaiDiaDiem",Integer.parseInt(params[1]));
                jsonObject.put("NoiDungCheckIn",params[2]);

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(jsonObject.toString());
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = new BufferedInputStream(connection.getInputStream());
                    InputStreamReader read = new InputStreamReader(is);
                    BufferedReader buff = new BufferedReader(read);
                    String result = "";
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        result += chunks;
                    }
                    return result;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

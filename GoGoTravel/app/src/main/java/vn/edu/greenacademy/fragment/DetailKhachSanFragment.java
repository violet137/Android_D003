package vn.edu.greenacademy.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.greenacademy.Adapter.LoadImageAdapter;
import vn.edu.greenacademy.asynctask.Image;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.Model.KhachSans;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailKhachSanFragment extends Fragment {

    ImageView imageKhachSan;
    TextView tvTenKhachSan;
    TextView tvDiaChi;
    TextView tvDanhGia;
    TextView tvSoLuotYeuThich;
    TextView tvSoLuotCheckIn;
    TextView tvSoLuotXem;
    TextView tvMoTa;
    GridView gridImage;
    List<ImageView> listImage;
    int[] imageId;

    public DetailKhachSanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detai_khach_san, container, false);

        imageKhachSan = (ImageView) view.findViewById(R.id.imageHotel);
        tvDanhGia = (TextView) view.findViewById(R.id.tvDanhGia);
        tvDiaChi = (TextView) view.findViewById(R.id.tvAddress);
        tvSoLuotCheckIn = (TextView) view.findViewById(R.id.tvCheckIn);
        tvSoLuotYeuThich = (TextView) view.findViewById(R.id.tvLike);
        tvSoLuotXem = (TextView) view.findViewById(R.id.tvSee);
        tvTenKhachSan = (TextView) view.findViewById(R.id.tvNameHotel);
        tvMoTa = (TextView) view.findViewById(R.id.tvDescribe);
        gridImage = (GridView) view.findViewById(R.id.gridImage);
        imageId = new int[]{R.drawable.ks1,R.drawable.ks2,R.drawable.ks3,R.drawable.ks4,R.drawable.ks5,R.drawable.ks6};

        final Bundle bundle = this.getArguments();
        if(bundle != null){
            KhachSans ks = (KhachSans) bundle.getSerializable("KhachSan");
            new Image(ks.getLinkAnh(),imageKhachSan).execute();
            tvSoLuotCheckIn.setText(String.valueOf(ks.getCheckIn()));
            tvDanhGia.setText(String.valueOf(ks.getDanhGia()));
            tvSoLuotXem.setText(String.valueOf(ks.getSoLuotXem()));
            tvDiaChi.setText(ks.getAddress());
            tvSoLuotYeuThich.setText(String.valueOf(ks.getYeuThich()));
            tvTenKhachSan.setText(ks.getTen());
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                tvMoTa.setText(Html.fromHtml(ks.getMoTa(),Html.FROM_HTML_MODE_LEGACY));  //chuyển html sang textview
            }else {
                tvMoTa.setText(Html.fromHtml(ks.getMoTa()));
            }
        }
        LoadImageAdapter adapter = new LoadImageAdapter(getContext(),R.layout.fragment_detai_khach_san,imageId, 0);
        gridImage.setAdapter(adapter);
        gridImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new ChiTietAnhFragment();
                Bundle bund = new Bundle();
                KhachSans ksan = (KhachSans) bundle.getSerializable("KhachSan");
                bund.putString("title",ksan.getTen());
                fragment.setArguments(bund);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(android.R.id.content, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }



}

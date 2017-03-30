package vn.edu.greenacademy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import vn.edu.greenacademy.adapter.LoadImageAdapter;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChiTietAnhFragment extends Fragment {

    TextView tvTitle;
    GridView gridChiTietAnh;
    int[] imageId;

    public ChiTietAnhFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_chi_tiet_anh, container, false);

        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        gridChiTietAnh = (GridView) v.findViewById(R.id.gridChiTietAnh);
        LoadImageAdapter adapter = new LoadImageAdapter(getContext(),R.layout.fragment_chi_tiet_anh,imageId);
        gridChiTietAnh.setAdapter(adapter);
        return v;
    }

}

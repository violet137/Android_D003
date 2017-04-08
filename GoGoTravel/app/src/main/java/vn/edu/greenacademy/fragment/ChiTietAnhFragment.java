package vn.edu.greenacademy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
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
    ImageButton imgbtnAnhZoom;

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
        imgbtnAnhZoom = (ImageButton) v.findViewById(R.id.imgbtnAnhZoom);

        imageId = new int[]{R.drawable.ks1,R.drawable.ks2,R.drawable.ks3,R.drawable.ks4,R.drawable.ks5,R.drawable.ks6};
        LoadImageAdapter adapter = new LoadImageAdapter(getContext(),R.layout.fragment_chi_tiet_anh,imageId, 1);
        gridChiTietAnh.setAdapter(adapter);
        final Bundle bundle = this.getArguments();
        if(bundle != null){
            String strTen = bundle.getString("title");
            tvTitle.setText(strTen);
        }
        gridChiTietAnh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridChiTietAnh.setVisibility(View.GONE);
                imgbtnAnhZoom.setVisibility(View.VISIBLE);
                imgbtnAnhZoom.setImageResource(imageId[i]);
                imgbtnAnhZoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridChiTietAnh.setVisibility(View.VISIBLE);
                        imgbtnAnhZoom.setVisibility(View.GONE);
                    }
                });
            }
        });
        return v;
    }
}

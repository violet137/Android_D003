package vn.edu.greenacademy.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.edu.greenacademy.Until.Constant;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuanAnFragment extends Fragment {

    TextView tvDemoQuanAn;

    public static QuanAnFragment instance;

    public static QuanAnFragment getInstance(){
        if (instance == null){
            instance = new QuanAnFragment();
        }

        return instance;
    }

    public QuanAnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quan_an, container, false);

        tvDemoQuanAn = (TextView) v.findViewById(R.id.tvDemoQuanAn);

        tvDemoQuanAn.setText(Constant.TEN_DIADIEM);

        return v;
    }

}

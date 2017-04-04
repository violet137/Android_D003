package vn.edu.greenacademy.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KhachSanFragment extends Fragment {

    TextView tvDemoKhachSan;
    static int id = 1;

    public static KhachSanFragment instance;

    public static KhachSanFragment getInstance(){
        if (instance == null){
            instance = new KhachSanFragment();
        }
        return instance;
    }

    public static void setId(int id) {
        KhachSanFragment.id = id;
    }

    public KhachSanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_khach_san, container, false);

        tvDemoKhachSan = (TextView) v.findViewById(R.id.tvDemoKhachSan);

        tvDemoKhachSan.setText(id+"");

        return v;
    }
}
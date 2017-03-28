package vn.edu.greenacademy.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaDiemFragment extends Fragment {

    public static DiaDiemFragment instance;

    public static DiaDiemFragment getInstance(){
        if (instance == null){
            instance = new DiaDiemFragment();
        }

        return instance;
    }

    public DiaDiemFragment() {
        // Required empty public constructor
    }

    //truyền dứ liệu giữa 2 fragment dùng Arguments


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dia_diem, container, false);
    }

}

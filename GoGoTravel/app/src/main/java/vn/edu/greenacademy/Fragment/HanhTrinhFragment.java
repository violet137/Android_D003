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
public class HanhTrinhFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hanh_trinh, container, false);
    }

}

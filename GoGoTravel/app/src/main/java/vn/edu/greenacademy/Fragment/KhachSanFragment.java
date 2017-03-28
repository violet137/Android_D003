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
public class KhachSanFragment extends Fragment {


    public static KhachSanFragment instance;

    public static KhachSanFragment getInstance(){
        if (instance == null){
            instance = new KhachSanFragment();
        }

        return instance;
    }

    public KhachSanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_khach_san, container, false);
    }

}

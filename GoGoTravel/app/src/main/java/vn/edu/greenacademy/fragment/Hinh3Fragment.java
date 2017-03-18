package vn.edu.greenacademy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hinh3Fragment extends Fragment {
    private static Hinh3Fragment instance = null;

    public static Hinh3Fragment getInstance(){
        if (instance==null){
            instance = new Hinh3Fragment();
        }
        return instance;
    }


    public Hinh3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hinh3, container, false);
    }

}

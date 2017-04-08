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
public class Hinh2Fragment extends Fragment {
    private static Hinh2Fragment instance = null;

    public static Hinh2Fragment getInstance(){
        if (instance==null){
            instance = new Hinh2Fragment();
        }


        
        return instance;
    }


    public Hinh2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hinh2, container, false);
    }

}

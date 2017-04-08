package vn.edu.greenacademy.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hinh4Fragment extends Fragment {

    ImageView ivIma4;


    private static Hinh4Fragment instance = null;

    public static Hinh4Fragment getInstance(){
        if (instance==null){
            instance = new Hinh4Fragment();
        }
        return instance;
    }


    public Hinh4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hinh4, container, false);

        ivIma4 = (ImageView) v.findViewById(R.id.ivIma4);
        ivIma4.setImageResource(R.drawable.tutorial_4);


        return v;
    }

}

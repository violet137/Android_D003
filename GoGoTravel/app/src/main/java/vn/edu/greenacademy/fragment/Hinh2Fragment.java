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
public class Hinh2Fragment extends Fragment {

    ImageView ivIma2;

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
        View v = inflater.inflate(R.layout.fragment_hinh2, container, false);

        ivIma2 = (ImageView) v.findViewById(R.id.ivIma2);
        ivIma2.setImageResource(R.drawable.tutorial_2);



        return v;
    }

}

package vn.edu.greenacademy.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hinh3Fragment extends Fragment {

    ImageView ivIma3;

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
        View v = inflater.inflate(R.layout.fragment_hinh3, container, false);

        ivIma3 = (ImageView) v.findViewById(R.id.ivIma3);
        ivIma3.setImageResource(R.drawable.tutorial_3);


        return v;
    }

}

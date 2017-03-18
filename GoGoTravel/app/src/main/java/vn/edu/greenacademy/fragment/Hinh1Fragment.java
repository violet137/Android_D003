package vn.edu.greenacademy.fragment;


import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
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
public class Hinh1Fragment extends Fragment {
    ImageView ivIma1;
    Button btnNext1;

    private static Hinh1Fragment instance = null;

    public static Hinh1Fragment getInstance(){
        if (instance==null){
            instance = new Hinh1Fragment();
        }
        return instance;
    }


    public Hinh1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hinh1, container, false);

        ivIma1 = (ImageView) v.findViewById(R.id.ivIma1);
        btnNext1 = (Button) v.findViewById(R.id.btnNext1);

        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivIma1.setImageResource(R.drawable.tutorial_1);
            }
        });
        return v;
    }


}

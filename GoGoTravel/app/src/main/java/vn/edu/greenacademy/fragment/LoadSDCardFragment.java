package vn.edu.greenacademy.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;

import vn.edu.greenacademy.adapter.ReviewImageAdapter;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadSDCardFragment extends Fragment {
    GridView gridsd;
    Button btnChuyen;
    private int columnIndex;
    private Cursor cursor;
    private static LoadSDCardFragment instance = null;
    ReviewImageAdapter reviewImageAdapter;

    public static LoadSDCardFragment getInstance(){
        if (instance==null){
            instance = new LoadSDCardFragment() ;
        }
        return instance;
    }


    public LoadSDCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_load_sdcard2, container, false);

        String[] projection = {MediaStore.Images.Thumbnails._ID};
        cursor = getActivity().getContentResolver().query(
                Uri.parse("file://" + Environment.getExternalStorageDirectory()
                + File.separator + "MyCreatedImages"), projection,
                null,null, MediaStore.Images.Thumbnails._ID);
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);

        gridsd = (GridView) v.findViewById(R.id.gridsd);
        btnChuyen = (Button) v.findViewById(R.id.btnChuyen);
        btnChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridsd.setAdapter(reviewImageAdapter);
            }
        });



        return v;
    }

}

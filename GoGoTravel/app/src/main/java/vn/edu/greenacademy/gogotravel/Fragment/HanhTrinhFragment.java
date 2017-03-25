package vn.edu.greenacademy.gogotravel.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import vn.edu.greenacademy.gogotravel.Adapter.ExpandableListView_HanhTrinh;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HanhTrinhFragment extends Fragment {
    private View view;
    ExpandableListView elvHanhTrinh;
    ExpandableListView_HanhTrinh adapterHanhTrinh;
    private String[] listHeader;
    private String[][] listChildren;

    public HanhTrinhFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listHeader = new String[]{
                "Header1","Header2","Header3","Header4"
        };
        listChildren=new String[][]{
                {"child1"},
                {"child2"},
                {"child3"},
                {"child4"},
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        if (view ==null){+
            view = inflater.inflate(R.layout.fragment_hanhtrinh, container, false);
//        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adapterHanhTrinh = new ExpandableListView_HanhTrinh(getContext(),listHeader,listChildren);
        elvHanhTrinh = (ExpandableListView) view.findViewById(R.id.elvHanhTrinh);
        elvHanhTrinh.setAdapter(adapterHanhTrinh);
        elvHanhTrinh.setGroupIndicator(null);
    }
}

package vn.edu.greenacademy.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.greenacademy.Adapter.SwipeDetailKhuVuc;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ViewPager mViewPager;
    SwipeDetailKhuVuc swipeDetailKhuVuc;
    View v;
    int id = 1;
    private TabLayout tabLayout;


    public static DetailFragment instance;

    public static DetailFragment getInstance(){
        if (instance == null){
            instance = new DetailFragment();
        }
        return instance;
    }

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detail, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = this.getArguments();
        id = bundle.getInt("khuvuc");

        swipeDetailKhuVuc = new SwipeDetailKhuVuc(getFragmentManager(),getContext(),id);
        mViewPager.setAdapter(swipeDetailKhuVuc);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ban_do);
        tabLayout.getTabAt(1).setIcon(R.drawable.hotel);
        tabLayout.getTabAt(2).setIcon(R.drawable.quan_an);


    }
}

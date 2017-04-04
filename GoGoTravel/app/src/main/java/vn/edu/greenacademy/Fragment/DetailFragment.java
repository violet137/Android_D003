package vn.edu.greenacademy.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

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
    Button btnBack;
    FrameLayout flDetail;

    KhuVucFragment khuVucFragment;


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
        btnBack = (Button) v.findViewById(R.id.btnBack);
        flDetail = (FrameLayout) v.findViewById(R.id.flDetail);
        Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();
        khuVucFragment = KhuVucFragment.getInstance();

        Bundle bundle = this.getArguments();
        id = bundle.getInt("khuvuc");

        swipeDetailKhuVuc = new SwipeDetailKhuVuc(getFragmentManager(),getContext(),id);
        mViewPager.setAdapter(swipeDetailKhuVuc);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ban_do);
        tabLayout.getTabAt(1).setIcon(R.drawable.hotel);
        tabLayout.getTabAt(2).setIcon(R.drawable.quan_an);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = this.getArguments();
        id = bundle.getInt("khuvuc");
        swipeDetailKhuVuc = new SwipeDetailKhuVuc(getFragmentManager(),getContext(),id);
        swipeDetailKhuVuc.notifyDataSetChanged();
        mViewPager.setAdapter(swipeDetailKhuVuc);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ban_do);
        tabLayout.getTabAt(1).setIcon(R.drawable.hotel);
        tabLayout.getTabAt(2).setIcon(R.drawable.quan_an);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"3",Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStack();
                mViewPager.removeAllViewsInLayout();
                tabLayout.removeAllViewsInLayout();
            }
        });
    }

//    public void callFragment(Fragment fragment){
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.flDetail, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }



}

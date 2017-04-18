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

import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ViewPager mViewPager;
    View v;
    int id = 1;
    private TabLayout tabLayout;
    Button btnBack;

    DiaDiemFragment diaDiemFragment;
    KhachSanFragment khachSanFragment;
    DanhsachQuan_LoaiFragment danhsachQuan_loaiFragment;

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
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        btnBack = (Button) v.findViewById(R.id.btnBack);

        Bundle bundle = this.getArguments();
        Constant.SET_ID = bundle.getInt("khuvuc");

        diaDiemFragment = DiaDiemFragment.getInstance();
        khachSanFragment = KhachSanFragment.getInstance();
        danhsachQuan_loaiFragment = DanhsachQuan_LoaiFragment.getInstance();

        tabLayout.addTab(tabLayout.newTab().setText("Địa Điểm"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Khách Sạn"));
        tabLayout.addTab(tabLayout.newTab().setText("Quán Ăn"));


        tabLayout.getTabAt(0).setIcon(R.drawable.ban_do);
        tabLayout.getTabAt(1).setIcon(R.drawable.hotel);
        tabLayout.getTabAt(2).setIcon(R.drawable.quan_an);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        return v;
    }
    @Override
    public void onResume() {
        callFragment(diaDiemFragment);
        super.onResume();

    }

    public void callFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.flChiTietKhuVuc, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0:
                callFragment(diaDiemFragment);
                break;
            case 1:
                callFragment(khachSanFragment);
                break;
            case 2:
                callFragment(danhsachQuan_loaiFragment);
                break;
        }
    }



}

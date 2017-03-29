package vn.edu.greenacademy.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import vn.edu.greenacademy.Adapter.SwipeDetailKhuVuc;
import vn.edu.greenacademy.Until.Constant;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ViewPager mViewPager;
    SwipeDetailKhuVuc swipeDetailKhuVuc;
    View v;
    android.app.ActionBar actionBar;

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

        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
                android.app.ActionBar.Tab t = actionBar.getSelectedTab();
                updateTabColor(actionBar.getSelectedTab(), Color.BLUE);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                updateTabColor(actionBar.getSelectedTab(), Color.WHITE);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                updateTabColor(actionBar.getSelectedTab(), Color.BLUE);
            }
        };

        for (int i = 0; i < Constant.DETAIL_KHU_VUC; i++) {
            actionBar.addTab(actionBar.newTab()
                            .setCustomView(mappingTitleTab(getActivity(),swipeDetailKhuVuc.getPageTitle(i))) //thêm ảnh ở đây
                            .setText(swipeDetailKhuVuc.getPageTitle(i))
                            .setTabListener((android.app.ActionBar.TabListener) tabListener));
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setNavigationMode(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //-----


        Bundle bundle = this.getArguments();
        if (bundle != null){
            int i =  bundle.getInt("KhuVuc");
            bundle.clear();
            bundle.putInt("IdDiaDiem",i);
        }

        Fragment fragment = new DiaDiemFragment();
        fragment.setArguments(bundle);

        swipeDetailKhuVuc = new SwipeDetailKhuVuc(getFragmentManager());
        mViewPager.setAdapter(swipeDetailKhuVuc);

        return v;
    }

    private View mappingTitleTab(Context mainActivity, CharSequence pageTitle) {
        FrameLayout view = (FrameLayout) LayoutInflater.from(mainActivity).inflate(R.layout.item_detail_swipe,null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    public void updateTabColor(android.app.ActionBar.Tab tab, int color){
        tab.getCustomView().setBackgroundColor(color);
    }

}

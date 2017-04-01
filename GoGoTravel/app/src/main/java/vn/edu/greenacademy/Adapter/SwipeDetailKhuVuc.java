package vn.edu.greenacademy.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.FrameLayout;

import java.util.LinkedList;
import java.util.List;

import vn.edu.greenacademy.Fragment.DiaDiemFragment;
import vn.edu.greenacademy.Fragment.KhachSanFragment;
import vn.edu.greenacademy.Fragment.QuanAnFragment;
import vn.edu.greenacademy.Until.Constant;

/**
 * Created by GIT on 3/28/2017.
 */

public class SwipeDetailKhuVuc extends FragmentStatePagerAdapter {

    String[] arrTitle = {"Địa Điểm", "Khách Sạn", "Quán Ăn"};
    private Context context;
    private List<Fragment> listSwipe = new LinkedList<Fragment>();

    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitle[position];
    }

    public SwipeDetailKhuVuc(FragmentManager fm,  int id) {
        super(fm);
//        this.context = context;

        DiaDiemFragment.setId(id);
        KhachSanFragment.setId(id);
        QuanAnFragment.setId(id);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = DiaDiemFragment.getInstance();
                break;
            case 1:
                fragment = KhachSanFragment.getInstance();
                break;
            case 2:
                fragment = QuanAnFragment.getInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Constant.DETAIL_KHU_VUC;
    }
}

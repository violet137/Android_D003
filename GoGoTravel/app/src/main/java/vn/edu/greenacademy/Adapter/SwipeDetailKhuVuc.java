package vn.edu.greenacademy.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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

    Fragment result = null;

    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitle[position];
    }

    public SwipeDetailKhuVuc(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        listSwipe.add(DiaDiemFragment.getInstance());
        listSwipe.add(KhachSanFragment.getInstance());
        listSwipe.add(QuanAnFragment.getInstance());
    }

    @Override
    public Fragment getItem(int position) {

        return listSwipe.get(position);
    }

    @Override
    public int getCount() {
        return Constant.DETAIL_KHU_VUC;
    }
}
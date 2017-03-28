package vn.edu.greenacademy.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.edu.greenacademy.Fragment.DiaDiemFragment;
import vn.edu.greenacademy.Fragment.KhachSanFragment;
import vn.edu.greenacademy.Fragment.QuanAnFragment;
import vn.edu.greenacademy.Until.Constant;

/**
 * Created by GIT on 3/28/2017.
 */

public class SwipeDetailKhuVuc extends FragmentPagerAdapter {

    String[] arrTitle = {"Địa Điểm", "Khách Sạn", "Quán Ăn"};


    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitle[position];
    }

    public SwipeDetailKhuVuc(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment result = null;
        switch (position){
            case 0:
                result = DiaDiemFragment.getInstance();
                break;
            case 1:
                result = KhachSanFragment.getInstance();
                break;
            case 2 :
                result = QuanAnFragment.getInstance();
                break;
        }
        if(position < 0 ){
            result = DiaDiemFragment.getInstance();
        }else if(position > arrTitle.length){
            result = QuanAnFragment.getInstance();
        }
        return result;
    }

    @Override
    public int getCount() {
        return Constant.DETAIL_KHU_VUC;
    }
}

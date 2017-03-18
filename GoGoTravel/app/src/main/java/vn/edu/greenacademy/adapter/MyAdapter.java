package vn.edu.greenacademy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.edu.greenacademy.fragment.Hinh1Fragment;
import vn.edu.greenacademy.fragment.Hinh2Fragment;
import vn.edu.greenacademy.fragment.Hinh3Fragment;
import vn.edu.greenacademy.fragment.Hinh4Fragment;
import vn.edu.greenacademy.utils.Constant;

/**
 * Created by User on 3/18/2017.
 */

public class MyAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEM = 4;
    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment result = null;
        switch (position) {
            case Constant.PAGE1:
                result = Hinh1Fragment.getInstance();
                break;
            case Constant.PAGE2:
                result = Hinh2Fragment.getInstance();
                break;
            case Constant.PAGE3:
                result = Hinh3Fragment.getInstance();
                break;
            case Constant.PAGE4:
                result = Hinh4Fragment.getInstance();
                break;
        }
        return result;
    }

    @Override
    public int getCount() {
        return NUM_ITEM;
    }

}

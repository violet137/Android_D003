package vn.edu.greenacademy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.edu.greenacademy.model.QuanAn;

/**
 * Created by MSI on 4/1/2017.
 */

public class ImageAdapter extends BaseAdapter{
    private Context context;
    List<QuanAn> list = new ArrayList<QuanAn>();
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85,85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            imageView = (ImageView) view;
        }
//        imageView.setImageResource(list.get(i).getLink());
        Picasso.with(context).load(list.get(i).getLink()).into(imageView);

        return imageView;
    }
}

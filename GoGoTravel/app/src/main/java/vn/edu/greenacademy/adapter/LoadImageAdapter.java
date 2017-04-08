package vn.edu.greenacademy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by GIT on 3/30/2017.
 */

public class LoadImageAdapter extends BaseAdapter {

    public List<ImageView> listImage;
    public LayoutInflater mlayoutInflater;
    private Context context = null;
    int layout;
    int[] imageId;
    int a;

    ImageView imageDetail;
    TextView tvSoLuongImage;

    public LoadImageAdapter(Context context, int layout, int[] imageId, int a) {
        this.context = context;
        this.layout = layout;
        this.imageId = imageId;
        this.a = a;
        mlayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = mlayoutInflater.inflate(R.layout.item_gridview_layout,null);
            imageDetail = (ImageView) convertView.findViewById(R.id.imageKhachSanDetail);
            tvSoLuongImage = (TextView) convertView.findViewById(R.id.tvSoLuongImage);
        }
        imageDetail.setImageResource(imageId[position]);
        if(a == 0){
            if(position == imageId.length - 1){
                tvSoLuongImage.setText("+23");
                tvSoLuongImage.setVisibility(View.VISIBLE);
            }else {
                tvSoLuongImage.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}

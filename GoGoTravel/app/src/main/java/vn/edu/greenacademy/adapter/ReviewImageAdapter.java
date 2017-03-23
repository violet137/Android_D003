package vn.edu.greenacademy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by User on 3/23/2017.
 */

public class ReviewImageAdapter extends BaseAdapter {
    List<Bitmap> datas = new LinkedList<>();
    Context context;

    @Override
    public int getCount() {
        return datas.size();
    }

    public void AddBitmap(Bitmap bitmap){
        datas.add(bitmap);
        notifyDataSetChanged();
    }

    public ReviewImageAdapter(Context context){
        this.context=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final CatalogHolder holder;
        View view = convertView;

        if (view == null){
            view.inflate(context, R.layout.check, parent);
            holder = new CatalogHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.imageView);
            holder.cbCheck = (CheckBox) view.findViewById(R.id.cbCheck);
            view.setTag(holder);
        }else{
            holder = (CatalogHolder) view.getTag();
        }
        holder.imageView.setImageBitmap(datas.get(position));

        return null;
    }

    class CatalogHolder {
        ImageView imageView;
        CheckBox cbCheck;
    }
}

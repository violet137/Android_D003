package vn.edu.greenacademy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

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
            LayoutInflater inflater=LayoutInflater.from(context);
            view=inflater.inflate(R.layout.check,parent,false);
            holder = new CatalogHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.imageView);

            view.setTag(holder);
        }else{
            holder = (CatalogHolder) view.getTag();
        }

        Bitmap bitmap = datas.get(position);
        holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,100,100,true));
//        holder.imageView.setImageBitmap(datas.get(position));

        return view;
    }

    class CatalogHolder {
        ImageView imageView;

    }
}

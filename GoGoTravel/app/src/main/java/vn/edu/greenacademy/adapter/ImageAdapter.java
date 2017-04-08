package vn.edu.greenacademy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.model.QuanAn;

/**
 * Created by MSI on 4/1/2017.
 */

public class ImageAdapter extends BaseAdapter{
    private Context mcontext;
    List<QuanAn> list = new ArrayList<QuanAn>();
    LayoutInflater inflater;

    public ImageAdapter(Context context, List<QuanAn> data){
        mcontext = context;
        list = data;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Row row;
        if(view == null){
            view = inflater.inflate(Row.LayOut,viewGroup,false);
            row = new Row(mcontext,view);
            view.setTag(row);
        }
            row = (Row) view.getTag();
            Picasso.with(mcontext).load(list.get(i).getLink()).into(row.imageView);
            if(i == 5){
                row.textView.setVisibility(View.VISIBLE);
                row.textView.setText("Show All");

            }else{
                row.textView.setVisibility(View.INVISIBLE);
            }



//            imageView = new ImageView(mcontext);
////            imageView.setLayoutParams(new GridView.LayoutParams(450,450));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8,8,8,8);
//        }else{
//            imageView = (ImageView) view;
//        }
//        imageView.setImageResource(list.get(i).getLink());
//        Picasso.with(mcontext).load(list.get(i).getLink()).into(imageView);



        return view;
    }

    private class Row{
        public static final int LayOut = R.layout.item_hinh;

        private Context context;
        private TextView textView;
        private ImageView imageView;

        private Row(Context context,View view){
            this.context = context;
            this.imageView = (ImageView) view.findViewById(R.id.ivHinhQ);
            this.textView = (TextView) view.findViewById(R.id.tvQ);
        }


    }

}
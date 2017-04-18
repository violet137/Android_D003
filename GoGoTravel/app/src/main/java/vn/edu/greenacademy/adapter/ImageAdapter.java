package vn.edu.greenacademy.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.Model.QuanAn;

/**
 * Created by MSI on 4/1/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mcontext;
    List<QuanAn> list = new ArrayList<QuanAn>();
    LayoutInflater inflater;

    public ImageAdapter(Context context, List<QuanAn> data) {
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
        if (view == null) {
            view = inflater.inflate(Row.LayOut, viewGroup, false);
            row = new Row(mcontext, view);
            view.setTag(row);
        }
        row = (Row) view.getTag();

        if (i == 5) {
            row.textView.setVisibility(View.VISIBLE);
            row.textView.setText("Show All");
            row.textView.setTextColor(mcontext.getResources().getColor(R.color.colorAccent));
//            row.imageView = new BlurImageView(mcontext);
            Glide.with(mcontext).load(list.get(i).getLink())
                    .bitmapTransform(new BlurTransformation(mcontext))
                    .into(row.imageView);

        } else {
            Picasso.with(mcontext).load(list.get(i).getLink()).into(row.imageView);
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

    class BlurImageView extends android.support.v7.widget.AppCompatImageView {
        Paint paint;


        private BlurImageView(Context context) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#aeffffff"));


        }
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

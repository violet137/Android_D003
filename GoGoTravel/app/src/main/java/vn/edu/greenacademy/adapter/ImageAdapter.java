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
        ImageView imageView;
        TextView textView;



        if(view == null){
            imageView = new ImageView(mcontext);
//            imageView.setLayoutParams(new GridView.LayoutParams(450,450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }else{
            imageView = (ImageView) view;
        }
//        imageView.setImageResource(list.get(i).getLink());
        Picasso.with(mcontext).load(list.get(i).getLink()).into(imageView);

        if(i == 5){

        }

        return imageView;
    }

    private class ViewModel{
        String text;
        String url;

        private ViewModel(String text,String url){
            this.text = text;
            this.url = url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    private class Row{
    }
}

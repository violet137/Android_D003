package vn.edu.greenacademy.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.model.QuanAn;

/**
 * Created by MSI on 3/25/2017.
 */

public class QuanNearAdapter extends ArrayAdapter{
    Activity context = null;
    List<QuanAn> list = null;
    int layout;


    public QuanNearAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<QuanAn> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layout = resource;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(layout,null);
            holder = new ViewHolder();

            holder.tvTen = (TextView) convertView.findViewById(R.id.tvTen);
            holder.tvCheckin = (TextView) convertView.findViewById(R.id.tvCheckin);
            holder.tvYeuthich = (TextView) convertView.findViewById(R.id.tvYeuThich);
            holder.tvGio = (TextView) convertView.findViewById(R.id.tvGio);

            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar) ;

            holder.ivHinh = (ImageView) convertView.findViewById(R.id.ivQuan);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        QuanAn temp = list.get(position);

        holder.tvTen.setText(temp.getTen());
        holder.tvCheckin.setText(String.valueOf(temp.getChenkin()));
        holder.tvYeuthich.setText(String.valueOf(temp.getYeuthich()));

        String mota = temp.getMota();
        String gio = split_mota_gio(mota);
        holder.tvGio.setText(gio);

        holder.ratingBar.setRating(temp.getDanhgia()/2);

        new DownloadImage(holder.ivHinh).execute(temp.getLink());

        return convertView;
    }

    private String split_mota_gio(String mota) {
        String result = null;
        char[] day = mota.toCharArray();
        int vt = 0;
        for (int i = 0; i < day.length; i++) {
            if(day[i] == '0'){
                vt = i;
                break;
            }
        }
        result = mota.substring(vt,vt+8);
        return result;
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView ivHinh) {
            this.imageView = ivHinh;
        }


        @Override
        protected Bitmap doInBackground(String... params) {
            String image_url = params[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(image_url);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    static class ViewHolder{
        ImageView ivHinh;
        TextView tvID,tvTen,tvYeuthich,tvCheckin,tvGio;
        RatingBar ratingBar;
    }
}

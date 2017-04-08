package vn.edu.greenacademy.Adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.Model.QuanAn;

/**
 * Created by MSI on 3/25/2017.
 */

public class QuanNearAdapter extends ArrayAdapter{
    Activity context = null;
    List<QuanAn> list = null;
    int layout;


    public QuanNearAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<QuanAn> objects) {
        super(context, resource, objects);
        ImageCache.getInstance().initializeCache();
        this.context = context;
        this.layout = resource;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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

//        new DownloadImage(holder.ivHinh).execute(temp.getLink());
//        holder.ivHinh.setFocusable(false);
//        holder.ivHinh.setFocusableInTouchMode(false);
        Picasso.with(getContext())
                .load(temp.getLink())
                .into(holder.ivHinh);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.ItemClick(position);
                }
            }
        });


        return convertView;
    }

    private QuanNearAdapter.ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void ItemClick(int position);
    }

    public void onItemClickListener(QuanNearAdapter.ItemClickListener listener){
        mItemClickListener = listener;
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


//            String name = image_url+".jpg";
//            File file = new File("/sdcard/"+name);
            try {
                URL url = new URL(image_url);

                Bitmap temp=ImageCache.getInstance().getImagefromStore(image_url);
                if(temp != null){
                    return temp;
                    // neu chua co trong cache thi download tu server
                }else{
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    ImageCache.getInstance().addImagetoStore(image_url,bitmap);
                }
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));

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

    public static class ImageCache{
        private LruCache<String,Bitmap> imageStore;
        private static ImageCache cache;

        public static ImageCache getInstance(){
            if(cache == null){
                cache = new ImageCache();
            }
            return cache;
        }

        public void initializeCache(){
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
            final int cacheSize = maxMemory/8;

            imageStore = new LruCache<String, Bitmap>(cacheSize){
                protected int sizeOf(String key,Bitmap value){
                    int bitmapByteCount = value.getRowBytes()*value.getHeight();

                    return bitmapByteCount/1024;
                }
            };
        }

        public void addImagetoStore(String key,Bitmap value){
            if(imageStore != null && imageStore.get(key) == null){
                imageStore.put(key,value);
            }
        }

        public Bitmap getImagefromStore(String key){
            if(key !=null){
                return imageStore.get(key);
            }else{
                return null;
            }
        }

        public void removeImagefromStore(String key){
            imageStore.remove(key);
        }

        public void clearCache(){
            if(imageStore != null){
                imageStore.evictAll();
            }
        }

    }


}

package vn.edu.greenacademy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by User on 3/30/2017.
 */

public class SDCardApdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<String> itemList = new ArrayList<String>();
    public ArrayList<String> check = new ArrayList<String>();
    public SDCardApdapter(Context con) {
        mContext = con;
    }

    public void add(String path){
        itemList.add(path);
    }

    public void clear(){
        itemList.clear();
    }

    public void remove(int index){
        itemList.remove(index);
    }

    @Override
    public int getCount() {
        return itemList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
//        ImageView ivSDCard;
        final CatalogHolder holder;
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.sdcard,parent,false);

            holder = new CatalogHolder();
            holder.ivSDCard = (ImageView) view.findViewById(R.id.ivSDCard);
            holder.cbCheck = (CheckBox) view.findViewById(R.id.cbCheck);
            assert view != null;
            view.setTag(holder);

//            ivSDCard = new ImageView(mContext);
//            ivSDCard.setLayoutParams(new GridView.LayoutParams(220,220));
//            ivSDCard.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            ivSDCard.setPadding(8,8,8,8);
        }else{
//            ivSDCard = (ImageView) view;
            holder = (CatalogHolder) view.getTag();
        }
        Bitmap bitmap = BitmapFactory.decodeFile(itemList.get(position));
        holder.ivSDCard.setImageBitmap(Bitmap.createScaledBitmap(bitmap,90,100,true));
        holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    check.add(itemList.get(position));
                }else {
                    check.remove(itemList.get(position));
                }
            }
        });


//        Bitmap bm = decodeSampleBitmapGromUri(itemList.get(position),220,220);
//        ivSDCard.setImageBitmap(bm);
//        return ivSDCard;
        return view;

    }

    public SDCardApdapter(final int position){
        ImageView ivSDCard;

        ivSDCard = new ImageView(mContext);
            ivSDCard.setLayoutParams(new GridView.LayoutParams(220,220));
            ivSDCard.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivSDCard.setPadding(8,8,8,8);

        Bitmap bm = decodeSampleBitmapGromUri(itemList.get(position),220,220);
        ivSDCard.setImageBitmap(bm);
        return ;
    }



    class CatalogHolder {
        ImageView ivSDCard;
        CheckBox cbCheck;
    }


        public Bitmap decodeSampleBitmapGromUri(String path, int reqWidth, int reqHeight){
        Bitmap bm = null;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth){
            if (width > height){
                inSampleSize = Math.round((float)height / (float) reqHeight);
            }else{
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
}


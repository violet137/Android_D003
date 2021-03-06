package vn.edu.greenacademy.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.Model.QuanAn;

/**
 * Created by MSI on 3/25/2017.
 */

public class QuanTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<QuanAn> list = new ArrayList<QuanAn>();

    public QuanTopAdapter(List<QuanAn> data){
        list = data;
    }

    private static final int Header = 0;
    private  static final int Content = 1;

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if(position == Header)
            return Header;
        else
            return Content;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_quanan, parent, false);
        if(viewType == Content) {
            return new ViewHolder_QuanAn(itemView);
        }else if(viewType == Header){
            return new ViewHolderHeader(itemView);
        }
        else
            throw new RuntimeException("Khong the tao giao dien");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String mota = list.get(position).getMota();
        String gio = split_mota_gio(mota);
        float sao = list.get(position).getDanhgia()/2;
        if(holder instanceof ViewHolder_QuanAn) {
            ((ViewHolder_QuanAn) holder).setData(
                    list.get(position).getLink(),
                    list.get(position).getTen(),
                    String.valueOf(list.get(position).getYeuthich()),
                    String.valueOf(list.get(position).getChenkin()),
                    sao, gio

            );
        }
        if(holder instanceof ViewHolderHeader){
            ViewHolderHeader holderHeader = (ViewHolderHeader) holder;
//            holderHeader.tvHeader.setVisibility(View.VISIBLE);
//            holderHeader.tvHeader.setText("Top Quán Ăn");
            holderHeader.setData(list.get(position).getLink(),
                    list.get(position).getTen(),
                    String.valueOf(list.get(position).getYeuthich()),
                    String.valueOf(list.get(position).getChenkin()),
                    sao, gio);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.ItemClick(holder.itemView,position);
                }
            }
        });
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void ItemClick(View view,int position);
    }

    public void onItemClickListener(ItemClickListener listener){
        mItemClickListener = listener;
    }

    public class ViewHolder_QuanAn extends RecyclerView.ViewHolder{
        private ImageView  ivHinh;
        private TextView tvTen,tvYeuThich,tvChenkin,tvGio;
        private RatingBar ratingBar;

        public ViewHolder_QuanAn(View itemView) {
            super(itemView);
            ivHinh = (ImageView) itemView.findViewById(R.id.ivQuan);
            tvTen = (TextView) itemView.findViewById(R.id.tvTen);
            tvYeuThich = (TextView) itemView.findViewById(R.id.tvYeuThich);
            tvChenkin = (TextView) itemView.findViewById(R.id.tvCheckin);
            tvGio = (TextView) itemView.findViewById(R.id.tvGio);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        public void setData(String link,String ten,String yt,String check,float rating,String gio){
//            new DownloadImage(ivHinh).execute(link);
            Picasso.with(itemView.getContext())
                    .load(link)
                    .into(ivHinh);
            tvTen.setText(ten);
            tvYeuThich.setText(yt);
            tvChenkin.setText(check);
            ratingBar.setRating(rating);
            tvGio.setText(gio);
        }
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder{
        private TextView tvHeader;
        private ImageView  ivHinh;
        private TextView tvTen,tvYeuThich,tvChenkin,tvGio;
        private RatingBar ratingBar;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            tvHeader = (TextView) itemView.findViewById(R.id.tvHeaderQuan);
            ivHinh = (ImageView) itemView.findViewById(R.id.ivQuan);
            tvTen = (TextView) itemView.findViewById(R.id.tvTen);
            tvYeuThich = (TextView) itemView.findViewById(R.id.tvYeuThich);
            tvChenkin = (TextView) itemView.findViewById(R.id.tvCheckin);
            tvGio = (TextView) itemView.findViewById(R.id.tvGio);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);


        }
        public void setData(String link,String ten,String yt,String check,float rating,String gio){
//            new DownloadImage(ivHinh).execute(link);
            Picasso.with(itemView.getContext())
                    .load(link)
                    .into(ivHinh);
            tvTen.setText(ten);
            tvYeuThich.setText(yt);
            tvChenkin.setText(check);
            ratingBar.setRating(rating);
            tvGio.setText(gio);
        }
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
}

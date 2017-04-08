package vn.edu.greenacademy.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.edu.greenacademy.gogotravel.R;
import vn.edu.greenacademy.Model.LoaiQuan;


/**
 * Created by MSI on 3/22/2017.
 */

public class LoaiQuanAdapter extends RecyclerView.Adapter<LoaiQuanAdapter.ViewHolder_LoaiQuan>{
    List<LoaiQuan> list = new ArrayList<LoaiQuan>();

    public LoaiQuanAdapter(List<LoaiQuan> data){
        list = data;
//        notifyDataSetChanged();
    }

    @Override
    public ViewHolder_LoaiQuan onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_loaiquanan,parent,false);
        return new ViewHolder_LoaiQuan(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_LoaiQuan holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.ItemClick(holder.itemView,position);
                }
            }
        });
        holder.setData(
                    list.get(position).getLink(),
                    list.get(position).getTen(),
                    String.valueOf(list.get(position).getSoluot())
        );

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

    public class ViewHolder_LoaiQuan extends RecyclerView.ViewHolder {
        private ImageView ivHinh;
        private TextView tvTen,tvSoluong;


        public ViewHolder_LoaiQuan(View itemView) {
            super(itemView);
            ivHinh = (ImageView) itemView.findViewById(R.id.ivLoaiquanan);
            tvTen = (TextView) itemView.findViewById(R.id.tvTenloai);
            tvSoluong = (TextView) itemView.findViewById(R.id.tvSoluong);
//            View.OnClickListener onClickListener=new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            };
//            ivHinh.setOnClickListener(onClickListener);
//            tvTen.setOnClickListener(onClickListener);
//            tvSoluong.setOnClickListener(onClickListener);
        }

        public void setData(String link,String ten,String sl){
//            new DownloadImage(ivHinh).execute(link);
            Picasso.with(itemView.getContext())
                    .load(link)
                    .into(ivHinh);
            tvTen.setText(ten);
            tvSoluong.setText(sl);
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

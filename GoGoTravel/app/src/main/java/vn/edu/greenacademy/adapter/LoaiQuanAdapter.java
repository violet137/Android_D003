package vn.edu.greenacademy.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
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

public class LoaiQuanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<LoaiQuan> list = new ArrayList<LoaiQuan>();

    public LoaiQuanAdapter(List<LoaiQuan> data){
        list = data;
//        notifyDataSetChanged();
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
        if (viewType == Header) {
            View itemView = inflater.inflate(R.layout.item_loaiquanan, parent, false);
            return new ViewHolderHeader(itemView);
        }
        else if (viewType == Content) {

            View itemView = inflater.inflate(R.layout.item_loaiquanan, parent, false);
            return new ViewHolder_LoaiQuan(itemView);
        }
        else
            throw new RuntimeException("Khong the tao giao dien");
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.ItemClick(holder.itemView,position);
                }
            }
        });

        if(holder instanceof ViewHolder_LoaiQuan){

            ((ViewHolder_LoaiQuan) holder).setData(
                    list.get(position).getLink(),
                    list.get(position).getTen(),
                    String.valueOf(list.get(position).getSoluot())
            );

        }

        if(holder instanceof ViewHolderHeader){
//            ((ViewHolderHeader) holder).header.setVisibility(View.VISIBLE);
//            ((ViewHolderHeader) holder).header.setText("Loại quán ăn");
//            ((ViewHolderHeader) holder).header.setTypeface(null, Typeface.BOLD_ITALIC);
//            ((ViewHolderHeader) holder).header.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            ((ViewHolderHeader) holder).header.setVisibility(View.VISIBLE);
            ((ViewHolderHeader) holder).header.setText("Loại quán ăn");
            ((ViewHolderHeader) holder).setData(
                    list.get(position).getLink(),
                    list.get(position).getTen(),
                    String.valueOf(list.get(position).getSoluot())
            );

        }

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

    public static class ViewHolderHeader extends RecyclerView.ViewHolder {
        private TextView header;
        private ImageView ivHinh;
        private TextView tvTen,tvSoluong;

        public ViewHolderHeader(View itemView){
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.tvHeader);
            ivHinh = (ImageView) itemView.findViewById(R.id.ivLoaiquanan);
            tvTen = (TextView) itemView.findViewById(R.id.tvTenloai);
            tvSoluong = (TextView) itemView.findViewById(R.id.tvSoluong);
//            header.setVisibility(View.VISIBLE);
//            header.setText("Loại quán ăn");
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

package vn.edu.greenacademy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.greenacademy.Model.NgayChuyenDiTranfers;
import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by User on 3/23/2017.
 */

public class ExpandableListView_HanhTrinh extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<NgayChuyenDiTranfers> lstNgayDi;

    public ExpandableListView_HanhTrinh(Context context, ArrayList<NgayChuyenDiTranfers> lstNgayDi) {
        this.context = context;
        this.lstNgayDi = lstNgayDi;
    }

    @Override
    public int getGroupCount() {
        return lstNgayDi.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return lstNgayDi.get(groupPosition).arrDiaDiem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lstNgayDi.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return lstNgayDi.get(groupPosition).getIdNgay();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return lstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getIdDiaDiem();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        HeaderViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_hanhtrinh_header,null);

            holder = new HeaderViewHolder();
            holder.tvNgayDi = (TextView) convertView.findViewById(R.id.tvNgayDi);
            holder.tvTongLike = (TextView) convertView.findViewById(R.id.tvTongLike);
            holder.tvTongAnh = (TextView) convertView.findViewById(R.id.tvTongAnh);

            convertView.setTag(holder);

        }else{
            holder = (HeaderViewHolder) convertView.getTag();
        }

        holder.tvNgayDi.setText(lstNgayDi.get(groupPosition).getNgayChuyenDi());
        holder.tvTongAnh.setText(String.valueOf(lstNgayDi.get(groupPosition).getSoLuongAnh()));
        holder.tvTongLike.setText(String.valueOf(lstNgayDi.get(groupPosition).getSoLuotLike()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildrenViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_hanhtrinh_item,null);

            holder = new ChildrenViewHolder();

            holder.tvLikeItem = (TextView) convertView.findViewById(R.id.tvLikeItem);
            holder.tvTenDiaDiem = (TextView) convertView.findViewById(R.id.tvTenDiaDiem);
            holder.tvPhotoItem = (TextView) convertView.findViewById(R.id.tvPhotoItem);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            holder.imvItem = (ImageView) convertView.findViewById(R.id.imvItem);
            holder.tvTimeItem = (TextView) convertView.findViewById(R.id.tvTimeItem);

            convertView.setTag(holder);

        }else{
            holder = (ChildrenViewHolder) convertView.getTag();
        }
            holder.tvTenDiaDiem.setText(lstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getTenDiaDiem());
            holder.tvLikeItem.setText(String.valueOf(lstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getSoLuotLike()));
            holder.tvDescription.setText(lstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getNoiDungCheckIn());
            holder.tvPhotoItem.setText(String.valueOf(lstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getSoLuongAnh()));
            holder.tvTimeItem.setText(lstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getNgayCheckIn());

            Picasso.with(context).load(lstNgayDi.get(groupPosition).arrDiaDiem.get(childPosition).getLinkAnh()).resize(130,100).into(holder.imvItem);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class HeaderViewHolder{
        TextView tvNgayDi,tvTongLike, tvTongAnh;
    }
    static class ChildrenViewHolder{
        TextView tvLikeItem,tvPhotoItem,tvTenDiaDiem,tvDescription,tvTimeItem;
        ImageView imvItem;
    }
}

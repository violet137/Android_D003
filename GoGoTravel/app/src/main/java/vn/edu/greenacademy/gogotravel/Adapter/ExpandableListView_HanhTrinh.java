package vn.edu.greenacademy.gogotravel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import vn.edu.greenacademy.gogotravel.Model.DiaDiemChuyenDiTranfers;
import vn.edu.greenacademy.gogotravel.Model.NgayChuyenDiTranfers;
import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by User on 3/23/2017.
 */

public class ExpandableListView_HanhTrinh extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<NgayChuyenDiTranfers> lstNgayDi;
    HashMap<String,ArrayList<DiaDiemChuyenDiTranfers>> lstItems;

    public ExpandableListView_HanhTrinh(Context context, ArrayList<NgayChuyenDiTranfers> lstNgayDi, HashMap<String, ArrayList<DiaDiemChuyenDiTranfers>> lstItems) {
        this.context = context;
        this.lstNgayDi = lstNgayDi;
        this.lstItems = lstItems;
    }

    @Override
    public int getGroupCount() {
        return lstNgayDi.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return lstItems.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lstNgayDi.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lstItems.get(lstNgayDi.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
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
            convertView = inflater.inflate(R.layout.listview_hanhtrinh_header,parent,false);
            holder = new HeaderViewHolder();
            holder.tvNgayDi = (TextView) convertView.findViewById(R.id.tvNgayDi);
            holder.tvTongLike = (TextView) convertView.findViewById(R.id.tvTongLike);
            holder.tvTongAnh = (TextView) convertView.findViewById(R.id.tvTongAnh);
        }
        else{
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.tvNgayDi.setText(lstNgayDi.get(groupPosition).getNgayChuyenDi());
        holder.tvTongLike.setText(String.valueOf(lstNgayDi.get(groupPosition).getSoLuotLike()));
        holder.tvTongAnh.setText(String.valueOf(lstNgayDi.get(groupPosition).getSoLuongAnh()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildrenViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_hanhtrinh_item,parent,false);
            holder = new ChildrenViewHolder();
            holder.tvLikeItem = (TextView) convertView.findViewById(R.id.tvLikeItem);
            holder.tvTenDiaDiem = (TextView) convertView.findViewById(R.id.tvTenDiaDiem);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            holder.tvPhotoItem = (TextView) convertView.findViewById(R.id.tvPhotoItem);
            holder.imvItem = (ImageView) convertView.findViewById(R.id.ImageItem);
        }else {
            holder = (ChildrenViewHolder) convertView.getTag();
        }

        holder.tvTimeItem.setText(((DiaDiemChuyenDiTranfers)getChild(groupPosition,childPosition)).getNgayCheckIn());
        holder.tvLikeItem.setText(((DiaDiemChuyenDiTranfers)getChild(groupPosition,childPosition)).getSoLuotLike());
        holder.tvDescription.setText(((DiaDiemChuyenDiTranfers)getChild(groupPosition,childPosition)).getNoiDungCheckIn());
        holder.tvPhotoItem.setText(((DiaDiemChuyenDiTranfers)getChild(groupPosition,childPosition)).getSoLuongAnh());
        holder.tvTenDiaDiem.setText(((DiaDiemChuyenDiTranfers)getChild(groupPosition,childPosition)).getTenDiaDiem());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    static class HeaderViewHolder{
        TextView tvNgayDi,tvTongLike, tvTongAnh;
    }
    static class ChildrenViewHolder{
        TextView tvLikeItem,tvPhotoItem,tvTenDiaDiem,tvDescription,tvTimeItem;
        ImageView imvItem;
    }
}

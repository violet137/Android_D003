package vn.edu.greenacademy.gogotravel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by User on 3/23/2017.
 */

public class ExpandableListView_HanhTrinh extends BaseExpandableListAdapter {
    private Context context;
    private String[] listHeader;
    private String[][] listChildren;

    public ExpandableListView_HanhTrinh(Context context,String[] listHeader, String[][] listChildren) {
        this.context = context;
        this.listHeader = listHeader;
        this.listChildren = listChildren;
    }

    @Override
    public int getGroupCount() {
        return listHeader.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return listChildren.length;
    }

    @Override
    public Object getGroup(int i) {
        return listHeader[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return listChildren[i][i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_hanhtrinh_header, viewGroup,false);

            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.tvNgayDi);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(getGroup(i).toString());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_hanhtrinh_item,viewGroup,false);
            holder = new ViewHolder();

            holder.text = (TextView) view.findViewById(R.id.tvTimeItem);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        holder.text.setText(getChild(i,i1).toString());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    private class ViewHolder{
        TextView text;
    }
}

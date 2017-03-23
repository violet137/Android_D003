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
    private ArrayList<String> listHeader;

    public ExpandableListView_HanhTrinh(Context context, ArrayList<String> listHeader) {
        this.context = context;
        this.listHeader = listHeader;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        String data = (String) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_hanhtrinh_header, null);
        }

        TextView tvNgayDi = (TextView) view.findViewById(R.id.tvNgayDi);

        tvNgayDi.setText(data);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
//        final String data = (String) getChild(groupPosition, childPosition);
//
//        if (view == null) {
//            LayoutInflater li = (LayoutInflater) this._context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = li.inflate(R.layout.listview_item, null);
//        }
//
//        TextView tvItem = (TextView) view.findViewById(R.id.tvItem);
//
//        tvItem.setText(data);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

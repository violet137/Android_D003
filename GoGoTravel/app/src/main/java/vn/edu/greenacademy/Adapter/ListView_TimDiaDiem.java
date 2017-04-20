package vn.edu.greenacademy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.greenacademy.Model.TimDiemTranfers;
import vn.edu.greenacademy.gogotravel.R;

/**
 * Created by DangQuang on 4/18/17.
 */

public class ListView_TimDiaDiem extends BaseAdapter {
    Context context;
    ArrayList<TimDiemTranfers> arrTenDiaDiem;

    public ListView_TimDiaDiem(Context context, ArrayList<TimDiemTranfers> arrTenDiaDiem) {
        this.context = context;
        this.arrTenDiaDiem = arrTenDiaDiem;
    }

    @Override
    public int getCount() {
        return arrTenDiaDiem.size();
    }

    @Override
    public Object getItem(int position) {
        return arrTenDiaDiem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrTenDiaDiem.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_diadiem_item,null);

            holder = new ViewHolder();
            holder.tvTenDiaDiemTim = (TextView) convertView.findViewById(R.id.tvTenDiaDiemTim);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTenDiaDiemTim.setText(arrTenDiaDiem.get(position).getTen());

        return convertView;
    }


    static class ViewHolder{
        TextView tvTenDiaDiemTim;
    }
}

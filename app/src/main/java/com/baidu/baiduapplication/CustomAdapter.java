package com.baidu.baiduapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Item> {
    private List<Item> dataList;

    public List<Item> getDataList() {
        return dataList;
    }

    @Override
    public int getCount() {
        return this.getDataList().size();
    }

    private LayoutInflater inflater;

    public CustomAdapter(Context context, List<Item> dataList) {
        super(context, 0, dataList);
        this.dataList = new ArrayList<>();
        this.dataList.addAll(dataList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_choose, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.tv_title);
            holder.tvType = convertView.findViewById(R.id.type);
            holder.tvArea = convertView.findViewById(R.id.area);
            holder.tvSite =  convertView.findViewById(R.id.site);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item data = dataList.get(position);
        holder.textView.setText(data.getTitle());
        holder.tvType.setText(data.getType());
        holder.tvArea.setText(data.getArea());
        holder.tvSite.setText(data.getSite());
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        TextView tvType;
        TextView tvArea;
        TextView tvSite;
    }
}
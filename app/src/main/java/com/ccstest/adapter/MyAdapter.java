package com.ccstest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ccstest.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> datas;

    public MyAdapter(Context context, List<String> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.number);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(datas.get(i));
        return view;
    }

    static class ViewHolder{
        TextView mTextView;
    }
}

package com.weifupro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weifupro.R;
import com.weifupro.bean.DateList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by "huangsays"  on 2017/8/4.16:57"huangays@gmail.com"
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>  {

    private Context mContext;
    private List<DateList> mList;

    public void setmList(List<DateList> mList) {
        this.mList = mList;
        if (mList == null){
            mList = new ArrayList<>();
        }
    }

    public ShopListAdapter(Context mContext, List<DateList> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ShopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopListViewHolder(View.inflate(mContext, R.layout.fragment_shop_item,null));
    }

    @Override
    public void onBindViewHolder(ShopListViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getShoplocation());
        holder.time.setText(mList.get(position).getVisitdate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ShopListViewHolder extends RecyclerView.ViewHolder {
        TextView time,name;

        public ShopListViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.shop_item_for_time);
            name = (TextView) itemView.findViewById(R.id.shop_item_for_name);
        }
    }
}

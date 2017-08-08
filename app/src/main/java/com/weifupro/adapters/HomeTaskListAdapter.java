package com.weifupro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weifupro.R;
import com.weifupro.bean.HomeTaskBody;

import java.util.List;

/**
 * Created by HuangJ on 2017/7/31.23:14
 */

public class HomeTaskListAdapter extends RecyclerView.Adapter<HomeTaskListAdapter.taskViewHolder>{

    private Context mContext;
    private List<HomeTaskBody> mList;
    private int number;
    private RecycleViewItemOnClick mRecycleViewItemOnclick;

    public HomeTaskListAdapter(Context mContext, List<HomeTaskBody> mList,int number) {
        this.mContext = mContext;
        this.mList = mList;
        this.number = number;
    }

    public void setmRecycleViewItemOnclick(RecycleViewItemOnClick mRecycleViewItemOnclick) {
        this.mRecycleViewItemOnclick = mRecycleViewItemOnclick;
    }

    @Override
    public taskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new taskViewHolder(View.inflate(mContext, R.layout.fragment_home_task_item,null));
    }

    @Override
    public void onBindViewHolder(taskViewHolder holder, final int position) {
        holder.title.setText(mList.get(position).getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecycleViewItemOnclick != null){
                        mRecycleViewItemOnclick.onReclycleViewitemonClick(v,position);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0){
            return number;
        }else {
            return 0;
        }
    }


    class taskViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public taskViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.fragment_home_task_item_title);
        }
    }

    public interface RecycleViewItemOnClick{
        public void onReclycleViewitemonClick(View view,int position);
    }
}

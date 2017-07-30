package com.weifupro.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weifupro.R;
import com.weifupro.bean.InfomationBody;

import java.util.List;

/**
 * Created by HuangJ on 2017/7/30.22:50
 */

public class InfomationListAdapter extends RecyclerView.Adapter<InfomationListAdapter.InfoViewHolder>{

    private Context mContext;
    private List<InfomationBody> mList;
    private int number;//控制展示条目数;

    public InfomationListAdapter(Context mContext, List<InfomationBody> mList, int number) {
        this.mContext = mContext;
        this.mList = mList;
        this.number = number;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.fragment_home_info_item,null);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        InfomationBody infomationBody = mList.get(position);
        holder.title.setText(infomationBody.getTitle());
        holder.smallText.setText(infomationBody.getSummary());
        if (!"".equals(infomationBody.getImgurl().trim()) && infomationBody.getImgurl() != null)
            Log.d("print", "onBindViewHolder: 下载图片");
            Picasso.with(mContext).load(infomationBody.getImgurl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (number > 0){
            return number;
        }else {
            return 0;
        }
    }

    class InfoViewHolder extends RecyclerView.ViewHolder{
        private ImageView arrow,img;
        private TextView title,smallText;
        public InfoViewHolder(View itemView) {
            super(itemView);
            arrow = (ImageView) itemView.findViewById(R.id.fragment_home_info_item_arrow);
            img = (ImageView) itemView.findViewById(R.id.fragment_home_info_item_img);
            smallText = (TextView) itemView.findViewById(R.id.fragment_home_info_item_context);
            title = (TextView) itemView.findViewById(R.id.fragment_home_info_item_title);
        }
    }
}

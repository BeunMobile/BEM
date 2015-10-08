package com.bilgiislem.sems.beunapp.AkademikTakvim.AkademikTakvimAdapter;

/**
 * Created by detro on 21.09.2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.R;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;

    public MyRecyclerAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyRecyclerAdapter.CustomViewHolder holder, int position) {
        FeedItem feedItem = feedItemList.get(position);
        holder.title.setText(feedItem.getTitle());
        holder.firstDay.setText(feedItem.getFirstDay());
        holder.firstMonth.setText(feedItem.getFirstMonth());
        holder.firstYear.setText(feedItem.getFirstYear());
        holder.firstColor.setBackgroundColor(Color.parseColor("#" + feedItem.getFirstDateColor()));
        holder.secDay.setText(feedItem.getSecDay());
        holder.secMonth.setText(feedItem.getSecMonth());
        holder.secYear.setText(feedItem.getSecYear());
        holder.secColor.setBackgroundColor(Color.parseColor("#" + feedItem.getSecDateColor()));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView firstDay;
        protected TextView firstMonth;
        protected TextView firstYear;
        protected TextView secDay;
        protected TextView secMonth;
        protected TextView secYear;
        protected RelativeLayout firstColor;
        protected RelativeLayout secColor;

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.titletext);
            this.firstDay = (TextView) view.findViewById(R.id.firstday);
            this.firstMonth = (TextView) view.findViewById(R.id.firstmonth);
            this.firstYear = (TextView) view.findViewById(R.id.firstyear);
            this.firstColor = (RelativeLayout) view.findViewById(R.id.firstcolor);
            this.secDay = (TextView) view.findViewById(R.id.secday);
            this.secMonth = (TextView) view.findViewById(R.id.secmonth);
            this.secYear = (TextView) view.findViewById(R.id.secyear);
            this.secColor = (RelativeLayout) view.findViewById(R.id.seccolor);
        }
    }
}
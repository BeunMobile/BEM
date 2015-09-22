package com.bilgiislem.sems.beunapp.AkademikTakvim.AkademikTakvimAdapter;

/**
 * Created by detro on 21.09.2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, null);
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

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.titletext);
            this.firstDay = (TextView) view.findViewById(R.id.firstday);
            this.firstMonth = (TextView) view.findViewById(R.id.firstmonth);
            this.firstYear = (TextView) view.findViewById(R.id.firstyear);
        }
    }
}
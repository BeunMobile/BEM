package com.bilgiislem.sems.beunapp.AkademikTakvim.AkademikTakvimAdapter;

/**
 * Created by detro on 21.09.2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;
import com.squareup.picasso.Picasso;

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
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView firstDate;
        protected TextView secDate;
        protected TextView title;

        public CustomViewHolder(View view) {
            super(view);
            //this.firstDate = (TextView) view.findViewById(R.id.date1_text);
            //this.secDate = (TextView) view.findViewById(R.id.date2_text);
            this.title = (TextView) view.findViewById(R.id.title_text);
        }
    }
}
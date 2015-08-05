package com.bilgiislem.sems.beunapp.YemekListesi;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by detro on 5.08.2015.
 */
public class PageChangerAdapter extends PagerAdapter {
    private Context ctx;

    public PageChangerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView tView = new TextView(ctx);
        position++;
        tView.setText("Page number: " + position);
        tView.setTextColor(Color.RED);
        tView.setTextSize(20);
        container.addView(tView);
        return tView;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
// TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}
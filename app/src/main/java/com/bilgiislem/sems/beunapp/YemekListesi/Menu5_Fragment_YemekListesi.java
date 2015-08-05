package com.bilgiislem.sems.beunapp.YemekListesi;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


import com.bilgiislem.sems.beunapp.R;

public class Menu5_Fragment_YemekListesi extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost host;
    private ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.menu5_yemek_listesi, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        host = (TabHost) rootview.findViewById(R.id.tabhost);
        pager = (ViewPager) rootview.findViewById(R.id.pager);

        host.setup();
        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator(getResources().getString(R.string.tab_pazartesi));
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(getResources().getString(R.string.tab_sali));
        host.addTab(spec);

        spec = host.newTabSpec("tab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator(getResources().getString(R.string.tab_carsamba));
        host.addTab(spec);

        spec = host.newTabSpec("tab4");
        spec.setContent(R.id.tab4);
        spec.setIndicator(getResources().getString(R.string.tab_persembe));
        host.addTab(spec);

        spec = host.newTabSpec("tab5");
        spec.setContent(R.id.tab5);
        spec.setIndicator(getResources().getString(R.string.tab_cuma));
        host.addTab(spec);

        pager.setAdapter(new PageChangerAdapter(getActivity()));
        pager.setOnPageChangeListener(this);
        host.setOnTabChangedListener(this);
        return rootview;
    }

    @Override
    public void onTabChanged(String tabId) {
        int pageNumber = 0;
        if (tabId.equals("tab1")) {
            pageNumber = 0;
        } else if (tabId.equals("tab2")) {
            pageNumber = 1;
        } else if (tabId.equals("tab3")) {
            pageNumber = 2;
        } else if (tabId.equals("tab4")) {
            pageNumber = 3;
        } else if (tabId.equals("tab5")) {
            pageNumber = 4;
        }
        pager.setCurrentItem(pageNumber);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int pageNumber) {
        host.setCurrentTab(pageNumber);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
package com.bilgiislem.sems.beunapp.AkademikTakvim;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgiislem.sems.beunapp.AkademikTakvim.AkademikTakvimSecenekler.BaharYariyili_Fragment;
import com.bilgiislem.sems.beunapp.AkademikTakvim.AkademikTakvimSecenekler.GuzYariyili_Fragment;
import com.bilgiislem.sems.beunapp.R;
import com.bilgiislem.sems.beunapp.YemekListesi.YemekListesiHaftalar.Carsamba_Fragment;
import com.bilgiislem.sems.beunapp.YemekListesi.YemekListesiHaftalar.Pazartesi_Fragment;
import com.bilgiislem.sems.beunapp.YemekListesi.YemekListesiHaftalar.Sali_Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GBYTab_Fragment extends android.support.v4.app.Fragment {


    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;

    public GBYTab_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gby, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        viewPager.setAdapter(new AkademikTakvimAdapter(getChildFragmentManager()));
        viewPager.getAdapter().notifyDataSetChanged();

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        return view;
    }

    class AkademikTakvimAdapter extends FragmentStatePagerAdapter {

        public AkademikTakvimAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GuzYariyili_Fragment();
                case 1:
                    return new BaharYariyili_Fragment();
                case 2:
                    return new Carsamba_Fragment();
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab_guz);
                case 1:
                    return getResources().getString(R.string.tab_bahar);
                case 2:
                    return getResources().getString(R.string.tab_yaz);
            }
            return null;
        }
    }


}


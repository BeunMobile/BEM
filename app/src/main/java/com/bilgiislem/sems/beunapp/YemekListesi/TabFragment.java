package com.bilgiislem.sems.beunapp.YemekListesi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bilgiislem.sems.beunapp.AnaSayfa.AnaSayfa_WebPage;
import com.bilgiislem.sems.beunapp.R;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.CarsambaTabFragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.CumaTabFragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.PazartesiTabFragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.PersembeTabFragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.SaliTabFragment;

import java.util.Calendar;
import java.util.TimeZone;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static Button buttonPdf;
    public static int int_items = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View view = inflater.inflate(R.layout.tab_layout, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        final int currentDay = localCalendar.get(Calendar.DATE);
        final int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
        final int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
        final int currentYear = localCalendar.get(Calendar.YEAR);

        final String cM = "" + currentMonth;
        final String cY = "" + currentYear;


        buttonPdf = (Button) view.findViewById(R.id.button_pdf);
        buttonPdf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YemekPDF.class);
                intent.putExtra("month", cM);
                intent.putExtra("year", cY);
                startActivity(intent);
            }
        });


        Log.i("Day And Month", "Day/Month :" + currentDay + "/" + currentMonth + "/" + currentDayOfWeek + "/" + currentYear);

        /**
         *Set an Apater for the View Pager
         */

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PazartesiTabFragment();
                case 1:
                    return new SaliTabFragment();
                case 2:
                    return new CarsambaTabFragment();
                case 3:
                    return new PersembeTabFragment();
                case 4:
                    return new CumaTabFragment();
            }
            return null;
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
                    return getResources().getString(R.string.tab_pazartesi);
                case 1:
                    return getResources().getString(R.string.tab_sali);
                case 2:
                    return getResources().getString(R.string.tab_carsamba);
                case 3:
                    return getResources().getString(R.string.tab_persembe);
                case 4:
                    return getResources().getString(R.string.tab_cuma);
            }
            return null;
        }
    }

}
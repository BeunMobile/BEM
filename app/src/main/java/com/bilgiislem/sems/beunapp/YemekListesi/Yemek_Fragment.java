package com.bilgiislem.sems.beunapp.YemekListesi;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.MainAndWeb.MainActivity;
import com.bilgiislem.sems.beunapp.R;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.Carsamba_Fragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.Cuma_Fragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.Pazartesi_Fragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.Persembe_Fragment;
import com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment.Sali_Fragment;

import java.util.Calendar;
import java.util.TimeZone;

public class Yemek_Fragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static Button buttonPdf;
    public static int int_items = 5;


    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    final int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
    final int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
    final int currentYear = localCalendar.get(Calendar.YEAR);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yemek, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.yemek_title));
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        final String cM = "" + currentMonth;
        final String cY = "" + currentYear;


        buttonPdf = (Button) view.findViewById(R.id.button_pdf);
        buttonPdf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PDFActivity.class);
                intent.putExtra("month", cM);
                intent.putExtra("year", cY);
                startActivity(intent);
            }
        });


        /**
         *Set an Apater for the View Pager
         */

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        viewPager.getAdapter().notifyDataSetChanged();

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */


        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                if (currentDayOfWeek != 1 && currentDayOfWeek != 7) {
                    viewPager.setCurrentItem(currentDayOfWeek - 2, false);
                } else {
                    Toast toast = Toast.makeText(getActivity(), R.string.closed_restaurant, Toast.LENGTH_SHORT);
                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                    if (v != null) v.setGravity(Gravity.CENTER);
                    toast.show();
                }
            }
        });

        return view;

    }

    //FragmentPagerAdapter
    class MyAdapter extends FragmentStatePagerAdapter {

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
                    return new Pazartesi_Fragment();
                case 1:
                    return new Sali_Fragment();
                case 2:
                    return new Carsamba_Fragment();
                case 3:
                    return new Persembe_Fragment();
                case 4:
                    return new Cuma_Fragment();
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

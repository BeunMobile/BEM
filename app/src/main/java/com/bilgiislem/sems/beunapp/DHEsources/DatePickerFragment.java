package com.bilgiislem.sems.beunapp.DHEsources;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.View;

import android.widget.DatePicker;
import android.app.Dialog;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    String dhe;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        /*
            Get Android DatePickerDialog without day
            - This code does not work
            THEME_DEVICE_DEFAULT_DARK
            THEME_DEVICE_DEFAULT_LIGHT
            - This code work fine
            THEME_HOLO_DARK
            THEME_HOLO_LIGHT
            THEME_TRADITIONAL
         */

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, year, month, day) {
            //DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,this,year, month, day){
            //DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_TRADITIONAL,this,year, month, day){
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                dhe = getShownIndex();

                int day = getContext().getResources().getIdentifier("android:id/day", null, null);

                if (day != 0) {
                    View dayPicker = findViewById(day);
                    if (dayPicker != null) {
                        //Set Day view visibility Off/Gone
                        dayPicker.setVisibility(View.GONE);
                    }
                }
            }
        };
        return dpd;
    }

    public String getShownIndex() {
        Bundle bundle = this.getArguments();
        dhe = bundle.getString("dhe");
        return dhe;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        String stringOfLink = "?yil=" + year + "&ay=" + (month + 1) + "&cins=" + dhe;

        if (dhe == "duyuru") {
            Fragment dhemyf = new Fragment();
            Bundle dbundle = new Bundle();
            dbundle.putString("dhelink", stringOfLink);
            dhemyf.setArguments(dbundle);
            Log.i("duyuru", stringOfLink);
        } else if (dhe == "haber") {
            ListFragment dhemyf = new ListFragment();
            Bundle dbundle = new Bundle();
            dbundle.putString("dhelink", stringOfLink);
            dhemyf.setArguments(dbundle);
        } else if (dhe == "etkinlik") {
            ListFragment dhemyf = new ListFragment();
            Bundle dbundle = new Bundle();
            dbundle.putString("dhelink", stringOfLink);
            dhemyf.setArguments(dbundle);
        }

        DHE_Month_Year dhe_month_year = new DHE_Month_Year();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.dhe_layout, dhe_month_year);
        ft.addToBackStack(null);
        ft.commit();
    }
}
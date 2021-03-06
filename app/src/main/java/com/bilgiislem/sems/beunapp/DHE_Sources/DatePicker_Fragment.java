package com.bilgiislem.sems.beunapp.DHE_Sources;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.app.Dialog;


import java.util.Calendar;
import java.util.Date;

public class DatePicker_Fragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    String dhe;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day) {
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
                        dayPicker.setVisibility(View.GONE);
                    }
                }
            }
        };

        dpd.getDatePicker().setMaxDate(new Date().getTime());

        return dpd;
    }

    public String getShownIndex() {
        Bundle bundle = this.getArguments();
        dhe = bundle.getString("dhe");
        return dhe;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String stringOfLink = "?yil=" + year + "&ay=" + (month + 1) + "&cins=" + dhe;

        Intent intent = new Intent(getActivity(), MYActivity.class);
        intent.putExtra("datelink", stringOfLink);
        intent.putExtra("title", (month + 1) + "/" + year);
        startActivity(intent);
    }


}
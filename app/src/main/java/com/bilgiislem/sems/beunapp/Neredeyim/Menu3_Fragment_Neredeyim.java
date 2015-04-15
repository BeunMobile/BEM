package com.bilgiislem.sems.beunapp.Neredeyim;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.AnaSayfa.AnaSayfa_WebPage;
import com.bilgiislem.sems.beunapp.R;

import java.util.Calendar;

/**
 * Created by sems on 17.03.2015.
 */
public class Menu3_Fragment_Neredeyim extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu3_layout, container, false);

        final Button sendFreeTextButton = (Button) view.findViewById(R.id.button_farabi);
        sendFreeTextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FarabiActivity.class);
                Menu3_Fragment_Neredeyim.this.startActivity(intent);
            }
        });
        return view;
    }
}

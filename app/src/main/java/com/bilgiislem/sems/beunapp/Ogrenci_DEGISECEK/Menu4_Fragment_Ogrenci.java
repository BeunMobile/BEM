package com.bilgiislem.sems.beunapp.Ogrenci_DEGISECEK;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bilgiislem.sems.beunapp.R;

/**
 * Created by sems on 17.03.2015.
 */
public class Menu4_Fragment_Ogrenci extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu4_layout_degisecek, container, false);

        final Button buttonekampus = (Button) view.findViewById(R.id.button_ekampus);
        buttonekampus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ekampus_WebPage.class);
                Menu4_Fragment_Ogrenci.this.startActivity(intent);
            }
        });

        final Button buttoneposta = (Button) view.findViewById(R.id.button_eposta);
        buttoneposta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Eposta_WebPage.class);
                Menu4_Fragment_Ogrenci.this.startActivity(intent);
            }
        });


        return view;
    }

}

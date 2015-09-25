package com.bilgiislem.sems.beunapp.AkademikTakvim;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bilgiislem.sems.beunapp.R;

public class Akademik_Fragment extends Fragment {


    public Akademik_Fragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akademik, container, false);

        Button gat = (Button) view.findViewById(R.id.button_gat);
        Button dhf = (Button) view.findViewById(R.id.button_dhf);
        Button enst = (Button) view.findViewById(R.id.button_enst);
        Button hazsin = (Button) view.findViewById(R.id.button_hazsin);
        Button tipfak = (Button) view.findViewById(R.id.button_tipfak);
        Button ulusogren = (Button) view.findViewById(R.id.button_ulusogren);
        Button ygi = (Button) view.findViewById(R.id.button_ygi);
        Button yazok = (Button) view.findViewById(R.id.button_yazok);
        Button yeteneksi = (Button) view.findViewById(R.id.button_yeteneksi);

        gat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","10");
                intent.putExtra("kategori_isim",getResources().getString(R.string.genel_takvim));
                startActivity(intent);
            }
        });

        dhf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","6");
                intent.putExtra("kategori_isim",getResources().getString(R.string.dis_takvim));
                startActivity(intent);
            }
        });

        enst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","9");
                intent.putExtra("kategori_isim",getResources().getString(R.string.enstitu_takvim));
                startActivity(intent);
            }
        });

        hazsin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","8");
                intent.putExtra("kategori_isim",getResources().getString(R.string.hazirlik_takvim));
                startActivity(intent);
            }
        });

        tipfak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","4");
                intent.putExtra("kategori_isim",getResources().getString(R.string.tip_takvim));
                startActivity(intent);
            }
        });

        ulusogren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","22");
                intent.putExtra("kategori_isim",getResources().getString(R.string.uluslarasi_takvim));
                startActivity(intent);
            }
        });

        /*ygi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","11");
                intent.putExtra("kategori_isim",R.string.yaz_takvim);
                startActivity(intent);
            }
        });*/

        yazok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","11");
                intent.putExtra("kategori_isim",getResources().getString(R.string.yaz_takvim));
                startActivity(intent);
            }
        });

        /*yeteneksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id","11");
                intent.putExtra("kategori_isim",R.string.yaz_takvim);
                startActivity(intent);
            }
        });*/

        return view;
    }


}

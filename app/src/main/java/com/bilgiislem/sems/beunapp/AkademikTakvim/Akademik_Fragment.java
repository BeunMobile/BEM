package com.bilgiislem.sems.beunapp.AkademikTakvim;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgiislem.sems.beunapp.MainActivity.MainActivity;
import com.bilgiislem.sems.beunapp.R;

public class Akademik_Fragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akademik_cardview, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.akademik_title));

        CardView gat = (CardView) view.findViewById(R.id.card_gat);
        CardView dhf = (CardView) view.findViewById(R.id.card_dhf);
        CardView enst = (CardView) view.findViewById(R.id.card_enst);
        CardView hazsin = (CardView) view.findViewById(R.id.card_hazsin);
        CardView tipfak = (CardView) view.findViewById(R.id.card_tipfak);
        CardView ulusogren = (CardView) view.findViewById(R.id.card_ulusogren);
        CardView ygi = (CardView) view.findViewById(R.id.card_ygi);
        CardView yazok = (CardView) view.findViewById(R.id.card_yazok);
        CardView yeteneksi = (CardView) view.findViewById(R.id.card_yeteneksi);

        gat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id", "10");
                intent.putExtra("kategori_isim", getResources().getString(R.string.genel_takvim));
                startActivity(intent);
            }
        });

        dhf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id", "6");
                intent.putExtra("kategori_isim", getResources().getString(R.string.dis_takvim));
                startActivity(intent);
            }
        });

        enst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id", "9");
                intent.putExtra("kategori_isim", getResources().getString(R.string.enstitu_takvim));
                startActivity(intent);
            }
        });

        hazsin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id", "8");
                intent.putExtra("kategori_isim", getResources().getString(R.string.hazirlik_takvim));
                startActivity(intent);
            }
        });

        tipfak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id", "4");
                intent.putExtra("kategori_isim", getResources().getString(R.string.tip_takvim));
                startActivity(intent);
            }
        });

        ulusogren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id", "22");
                intent.putExtra("kategori_isim", getResources().getString(R.string.uluslarasi_takvim));
                startActivity(intent);
            }
        });

        ygi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.ygi_picker);
                builder.setItems(R.array.ygi_names, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent kayg = new Intent(getActivity(), GBYActivity.class);
                                kayg.putExtra("kategori_id", "16");
                                kayg.putExtra("kategori_isim", getResources().getString(R.string.kayg_takvim));
                                startActivity(kayg);
                                break;
                            case 1:
                                Intent kyg = new Intent(getActivity(), GBYActivity.class);
                                kyg.putExtra("kategori_id", "18");
                                kyg.putExtra("kategori_isim", getResources().getString(R.string.kyg_takvim));
                                startActivity(kyg);
                                break;
                            case 2:
                                Intent myp = new Intent(getActivity(), GBYActivity.class);
                                myp.putExtra("kategori_id", "17");
                                myp.putExtra("kategori_isim", getResources().getString(R.string.myp_takvim));
                                startActivity(myp);
                                break;
                            case 3:
                                Intent yca = new Intent(getActivity(), GBYActivity.class);
                                yca.putExtra("kategori_id", "19");
                                yca.putExtra("kategori_isim", getResources().getString(R.string.yca_takvim));
                                startActivity(yca);
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        yazok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GBYActivity.class);
                intent.putExtra("kategori_id", "11");
                intent.putExtra("kategori_isim", getResources().getString(R.string.yaz_takvim));
                startActivity(intent);
            }
        });

        yeteneksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.ys_picker);
                builder.setItems(R.array.ys_names, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent kayg = new Intent(getActivity(), GBYActivity.class);
                                kayg.putExtra("kategori_id", "23");
                                kayg.putExtra("kategori_isim", getResources().getString(R.string.besy_takvim));
                                startActivity(kayg);
                                break;
                            case 1:
                                Intent kyg = new Intent(getActivity(), GBYActivity.class);
                                kyg.putExtra("kategori_id", "21");
                                kyg.putExtra("kategori_isim", getResources().getString(R.string.dk_takvim));
                                startActivity(kyg);
                                break;
                            case 2:
                                Intent myp = new Intent(getActivity(), GBYActivity.class);
                                myp.putExtra("kategori_id", "15");
                                myp.putExtra("kategori_isim", getResources().getString(R.string.gsf_takvim));
                                startActivity(myp);
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return view;
    }



}

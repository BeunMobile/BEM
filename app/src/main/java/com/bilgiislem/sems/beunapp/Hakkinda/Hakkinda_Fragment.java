package com.bilgiislem.sems.beunapp.Hakkinda;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.MainActivity.MainActivity;
import com.bilgiislem.sems.beunapp.R;

/**
 * Created by detro on 16.10.2015.
 */

public class Hakkinda_Fragment extends Fragment {
    TextView sems, mobil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hakkinda, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.about_about));
        sems = (TextView) view.findViewById(R.id.sems_text);
        mobil = (TextView) view.findViewById(R.id.mobil_beun);


        sems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mobil.getText().toString()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "GeriBildirim/FeedBack");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Geri Bildirim Açýklamasý :");
                startActivity(Intent.createChooser(emailIntent, "E-Posta Gönder ..."));
            }
        });

        mobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mobil.getText().toString()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "GeriBildirim/FeedBack");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Geri Bildirim Açýklamasý :");
                startActivity(Intent.createChooser(emailIntent, "E-Posta Gönder ..."));
            }
        });


        return view;
    }
}

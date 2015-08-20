package com.bilgiislem.sems.beunapp.AnaSayfa;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import com.bilgiislem.sems.beunapp.R;

public class AnaSayfa_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final Button sendFreeTextButton = (Button) view.findViewById(R.id.ana_sayfa_button);
        sendFreeTextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnaSayfaActivity.class);
                AnaSayfa_Fragment.this.startActivity(intent);
            }
        });
        return view;
    }
}
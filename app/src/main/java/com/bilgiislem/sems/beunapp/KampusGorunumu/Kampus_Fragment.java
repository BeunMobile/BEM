package com.bilgiislem.sems.beunapp.KampusGorunumu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bilgiislem.sems.beunapp.MainAndWeb.MainActivity;
import com.bilgiislem.sems.beunapp.R;

/**
 * Created by sems on 17.03.2015.
 */
public class Kampus_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kampus, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.kampus_title));

        ImageButton buttonfarabi = (ImageButton) view.findViewById(R.id.button_farabi);
        buttonfarabi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("maps", "farabi");
                Kampus_Fragment.this.startActivity(intent);
            }
        });

        ImageButton buttonibnisina = (ImageButton) view.findViewById(R.id.button_ibnisina);
        buttonibnisina.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("maps", "ibni");
                Kampus_Fragment.this.startActivity(intent);
            }
        });
        return view;
    }
}

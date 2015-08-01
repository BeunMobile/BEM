package com.bilgiislem.sems.beunapp.KampusGorunumu;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bilgiislem.sems.beunapp.R;

/**
 * Created by sems on 17.03.2015.
 */
public class Menu9_Fragment_Kampus_Gorunumu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu9_layout_kampus_gorunumu, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageButton buttonfarabi = (ImageButton) view.findViewById(R.id.button_farabi);
        buttonfarabi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FarabiActivity.class);
                Menu9_Fragment_Kampus_Gorunumu.this.startActivity(intent);
            }
        });
        return view;
    }
}

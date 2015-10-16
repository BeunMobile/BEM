package com.bilgiislem.sems.beunapp.Hakkinda;

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
    TextView about_beta;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_iletisim, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.about_about));
        about_beta = (TextView) view.findViewById(R.id.about_beta);
        return view;
    }
}

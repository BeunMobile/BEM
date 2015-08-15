package com.bilgiislem.sems.beunapp.YemekListesi.HaftalarFragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgiislem.sems.beunapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CumaTabFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cuma_tab, null);
    }
}
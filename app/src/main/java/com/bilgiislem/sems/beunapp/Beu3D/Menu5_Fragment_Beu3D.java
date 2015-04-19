package com.bilgiislem.sems.beunapp.Beu3D;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.bilgiislem.sems.beunapp.AnaSayfa.AnaSayfa_WebPage;
import com.bilgiislem.sems.beunapp.R;

/**
 * Created by sems on 17.03.2015.
 */
public class Menu5_Fragment_Beu3D extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu5_layout, container, false);
        final Button sendFreeTextButton = (Button) view.findViewById(R.id.beu_3d_button);
        sendFreeTextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Beu_3DPage.class);
                Menu5_Fragment_Beu3D.this.startActivity(intent);
            }
        });
        return view;
    }

}

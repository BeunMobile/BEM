package com.bilgiislem.sems.beunapp.Beu3D;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
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

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Cihazýnýz yavaþ çalýþabilir!")
                        .setMessage("BEÜ 3D gezinti uygulamasý üst seviye cihazlarda hýzlý bir þekilde çalýþýr yine de devam etmek istiyormusunuz ?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(getActivity(), Beu_3DPage.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Ýþlem iptal edildi...", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        return view;
    }

}

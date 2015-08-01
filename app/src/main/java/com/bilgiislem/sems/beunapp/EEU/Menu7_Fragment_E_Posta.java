package com.bilgiislem.sems.beunapp.EEU;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

public class Menu7_Fragment_E_Posta extends Fragment {
    View rootview;
    private String url_eposta = "http://stu.karaelmas.edu.tr/sm/src/login.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.web_page_layout, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);

        WebView webView = (WebView) rootview.findViewById(R.id.web_page);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url_eposta);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        Toast.makeText(getActivity(), "E-Posta Sayfaniz gecici olarak servis disidir...", Toast.LENGTH_LONG).show();
        return rootview;
    }
}

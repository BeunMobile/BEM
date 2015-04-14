package com.bilgiislem.sems.beunapp;

import android.app.Fragment;
import android.os.Bundle;
import android.renderscript.Element;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by sems on 17.03.2015.
 */
public class Menu2_Fragment extends Fragment{
    View rootview;

    String Url ="http://www.google.com/";
    String html = "<html><body class=\"arkaplan\" onload=\"baslangic()\"><div class=\"sayfa\"><div class=\"govde\"><table style=\"margin-left: 4px;\"><tbody><tr><td id=\"yazilar\"><div id=\"yazi-baslik\">Duyurular</div></td></tr></tbody></table></div></div></body></html>";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.menu2_layout, container, false);
        WebView webView = (WebView) rootview.findViewById(R.id.neredeyim_page);
        webView.loadUrl(Url);
        webView.loadData(html, "text/html", null);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(50);
        return rootview;
    }
}

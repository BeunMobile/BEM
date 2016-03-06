package com.bilgiislem.sems.beunapp.AnaSayfa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bilgiislem.sems.beunapp.DHE_Sources.IcerikActivity;
import com.bilgiislem.sems.beunapp.DHE_Sources.ServiceHandler;
import com.bilgiislem.sems.beunapp.MainActivity.MainActivity;
import com.bilgiislem.sems.beunapp.R;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class AnaSayfa_Fragment extends Fragment {

    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    final int currentDay = localCalendar.get(Calendar.DATE);
    final int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
    final int currentYear = localCalendar.get(Calendar.YEAR);
    final int dayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);

    private static String urlbeun = "http://w3.beun.edu.tr/";
    //private static String url_duyuru = "http://w3.beun.edu.tr/mobil-duyurular/";
    //private static String url_haber = "http://w3.beun.edu.tr/mobil-haberler/";
    private static String url_etkinlik = "http://w3.beun.edu.tr/mobil-etkinlikler/";
    //private static String url_slider = "http://w3.beun.edu.tr/mobil-slayt/";
    String url_yemek = "http://w3.beun.edu.tr/yemek_listesi/";// veri/?ay=10&yil=2015&gun=30";

    private static final String TAG_S1 = "s1";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";
    private static final String TAG_GUN = "gun";
    private static final String TAG_AY = "ay";
    private static final String TAG_LISTE = "liste";
    private static final String TAG_YEMEKLER = "yemekler";
    private static final String TAG_YEMEK_ISIM = "isim";
    private static final String TAG_CINS = "cins";

    JSONArray s1 = null;
    JSONArray liste = null;

    ArrayList<String> imageList;
    ArrayList<String> imageLinkList;
    ViewFlipper viewFlipper;
    private float lastX;

    TextView emptyDuyuru, setDuyuru, loadDuyuru, emptyHaber, setHaber, loadHaber, emptyEtkinlik,
            setEtkinlik, loadEtkinlik, setBaslikEtkinlik, corbaText, yemek1Text, yemek2Text, digerText;
    CardView cardDuyuru, cardHaber, cardEtkinlik;

    String baslikDuyuru, adresDuyuru, baslikHaber, adresHaber, baslikEtkinlik, adresEtkinlik,
            gunEtkinlik, ayEtkinlik, genelYemek, cinsYemek, corbaYemek, yemek1Yemek, yemek2Yemek, digerYemek;
    String[] months;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.ana_sayfa_title));

        url_yemek = "http://w3.beun.edu.tr/yemek_listesi/veri/?ay=" + currentMonth + "&yil=" + currentYear + "&gun=" + currentDay;

        months = getResources().getStringArray(R.array.months);

        cardDuyuru = (CardView) view.findViewById(R.id.card_duyuru);
        cardHaber = (CardView) view.findViewById(R.id.card_haber);
        cardEtkinlik = (CardView) view.findViewById(R.id.card_etkinlik);

        emptyDuyuru = (TextView) view.findViewById(R.id.card_no_duyuru);
        setDuyuru = (TextView) view.findViewById(R.id.card_duyuru_icerik);
        loadDuyuru = (TextView) view.findViewById(R.id.card_duyuru_loading);
        emptyHaber = (TextView) view.findViewById(R.id.card_no_haber);
        setHaber = (TextView) view.findViewById(R.id.card_haber_icerik);
        loadHaber = (TextView) view.findViewById(R.id.card_haber_loading);
        emptyEtkinlik = (TextView) view.findViewById(R.id.card_no_etkinlik);
        setEtkinlik = (TextView) view.findViewById(R.id.card_etkinlik_icerik);
        loadEtkinlik = (TextView) view.findViewById(R.id.card_etkinlik_loading);
        setBaslikEtkinlik = (TextView) view.findViewById(R.id.card_etkinlik_baslik);
        corbaText = (TextView) view.findViewById(R.id.card_food_corba);
        yemek1Text = (TextView) view.findViewById(R.id.card_food_yemek1);
        yemek2Text = (TextView) view.findViewById(R.id.card_food_yemek2);
        digerText = (TextView) view.findViewById(R.id.card_food_diger);

        new getAnasayfaJsoup().execute();

        //new getDuyuruJSON().execute();
        //new getHaberJSON().execute();
        //new getEtkinlikJSON().execute();
        if (dayOfWeek != 1 && dayOfWeek != 7) {
            new getYemekJSON().execute();
        } else {

        }

        viewFlipper = (ViewFlipper) view.findViewById(R.id.image_flipper);
        viewFlipper.setVisibility(View.INVISIBLE);
        imageList = new ArrayList<String>();
        imageLinkList = new ArrayList<String>();
        //new JSONImageSlider().execute(url_slider);
        FlipperEvents();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void FlipperEvents() {
        final Handler mHandler = new Handler();

        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = motionEvent.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float currentX = motionEvent.getX();
                        if (lastX < currentX) {
                            if (viewFlipper.getDisplayedChild() == 0) {
                                return true;
                            }
                            viewFlipper.setInAnimation(getActivity(), R.anim.slide_in_from_left);
                            viewFlipper.setOutAnimation(getActivity(), R.anim.slide_out_to_right);
                            viewFlipper.setDisplayedChild(viewFlipper.getDisplayedChild() - 1);
                        } else if (lastX > currentX) {
                            if (viewFlipper.getDisplayedChild() == imageList.size()) {
                                return true;
                            }
                            viewFlipper.setInAnimation(getActivity(), R.anim.slide_in_from_right);
                            viewFlipper.setOutAnimation(getActivity(), R.anim.slide_out_to_left);
                            viewFlipper.setDisplayedChild(viewFlipper.getDisplayedChild() + 1);
                        } else if (lastX == currentX) {
                            Intent intent = new Intent(getActivity(), WebActivity.class);
                            intent.putExtra("web", imageLinkList.get(viewFlipper.getDisplayedChild()));
                            AnaSayfa_Fragment.this.startActivity(intent);
                        }
                        return true;
                }
                return false;
            }
        });

        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                AnimateandSlideShow();
            }
        };

        int delay = 500;
        int period = 10000;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.post(mUpdateResults);
            }
        }, delay, period);

    }

/*    private class JSONImageSlider extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("s1");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        imageList.add("http://w3.beun.edu.tr/dosyalar/" + object.getString("gorsel"));
                        imageLinkList.add(object.getString("adres"));
                    }
                    return true;
                }
            } catch (ParseException | IOException | JSONException e1) {
                e1.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            try {
                if (!result) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.no_connection, Toast.LENGTH_SHORT).show();
                } else {
                    viewFlipper.setVisibility(View.VISIBLE);
                    setFlipperImage(imageList);
                }
            } catch (NullPointerException e) {
                Log.d("NPE", e.toString());
            }

        }
    }*/

    private class getAnasayfaJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(urlbeun).get();
                Elements slider = doc.select("div.jqslider > ul").select("li");

                for (int i = 0; i < slider.size(); i++) {
                    imageList.add(slider.get(i).select("img[src$=.jpg]").attr("abs:src"));
                    imageLinkList.add(slider.get(i).select("a").attr("abs:href"));
                }

                int i = 0;
                for (Element element : doc.select("ul[class=dli]")) {
                    if (i == 1) {
                        break;
                    }
                    baslikDuyuru = element.text();
                    adresDuyuru = element.select("li a").attr("abs:href");
                    i++;
                }
                i = 0;
                for (Element element : doc.select("ul[class=eli]")) {
                    if (i == 1) {
                        break;
                    }
                    baslikHaber = element.text();
                    adresHaber = element.select("li a").attr("abs:href");
                    i++;
                }
                i = 0;
                for (Element element : doc.select("ul[id=takvimul]")) {
                    if (i == 1) {
                        break;
                    }
                    baslikEtkinlik = element.select("li > div > ol > li > ol > li > a").select("span[class=ai1ec-event-title]").text();
                    adresEtkinlik = element.select("li > div > ol > li > ol > li").select("a").attr("abs:href");
                    i++;
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try {
                viewFlipper.setVisibility(View.VISIBLE);
                setFlipperImage(imageList);
            } catch (NullPointerException | IllegalArgumentException e) {
                Log.d("NPE", e.toString());
            }

            loadDuyuru.setVisibility(View.GONE);
            try {
                if (!baslikDuyuru.isEmpty()) {
                    setDuyuru.setText(baslikDuyuru);
                    cardDuyuru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresDuyuru);
                            intent.putExtra("baslik", baslikDuyuru);
                            startActivity(intent);
                        }
                    });
                    setDuyuru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresDuyuru);
                            intent.putExtra("baslik", baslikDuyuru);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyDuyuru.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyDuyuru.setVisibility(View.VISIBLE);
            }

            loadHaber.setVisibility(View.GONE);
            try {
                if (!baslikHaber.isEmpty()) {
                    setHaber.setText(baslikHaber);
                    cardHaber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresHaber);
                            intent.putExtra("baslik", baslikHaber);
                            startActivity(intent);
                        }
                    });
                    setHaber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresHaber);
                            intent.putExtra("baslik", baslikHaber);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyHaber.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyHaber.setVisibility(View.VISIBLE);
            }

            loadEtkinlik.setVisibility(View.GONE);
            try {
                if (!baslikEtkinlik.isEmpty()) {
                    //setBaslikEtkinlik.setText(gunEtkinlik + "\n" + months[Integer.parseInt(ayEtkinlik) - 1]);
                    setEtkinlik.setText(baslikEtkinlik);
                    cardEtkinlik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresEtkinlik);
                            intent.putExtra("baslik", baslikEtkinlik);
                            startActivity(intent);
                        }
                    });
                    setEtkinlik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresEtkinlik);
                            intent.putExtra("baslik", baslikEtkinlik);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyEtkinlik.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyEtkinlik.setVisibility(View.VISIBLE);
            }

            super.onPostExecute(aVoid);
        }
    }

    /*private class getDuyuruJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url_duyuru, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    s1 = jsonObj.getJSONArray(TAG_S1);
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = s1.getJSONObject(i);
                        baslikDuyuru = c.getString(TAG_BASLIK);
                        adresDuyuru = c.getString(TAG_ADRES);
                        baslikDuyuru = html2text(baslikDuyuru);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loadDuyuru.setVisibility(View.GONE);
            try {
                if (!baslikDuyuru.isEmpty()) {
                    setDuyuru.setText(baslikDuyuru);
                    cardDuyuru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresDuyuru);
                            intent.putExtra("baslik", baslikDuyuru);
                            startActivity(intent);
                        }
                    });
                    setDuyuru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresDuyuru);
                            intent.putExtra("baslik", baslikDuyuru);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyDuyuru.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyDuyuru.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }*/

    /*private class getHaberJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url_haber, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    s1 = jsonObj.getJSONArray(TAG_S1);
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = s1.getJSONObject(i);
                        baslikHaber = c.getString(TAG_BASLIK);
                        adresHaber = c.getString(TAG_ADRES);
                        baslikHaber = html2text(baslikHaber);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loadHaber.setVisibility(View.GONE);
            try {
                if (!baslikHaber.isEmpty()) {
                    setHaber.setText(baslikHaber);
                    cardHaber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresHaber);
                            intent.putExtra("baslik", baslikHaber);
                            startActivity(intent);
                        }
                    });
                    setHaber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresHaber);
                            intent.putExtra("baslik", baslikHaber);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyHaber.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyHaber.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }*/


    private class getEtkinlikJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url_etkinlik, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    s1 = jsonObj.getJSONArray(TAG_S1);
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = s1.getJSONObject(i);
                        baslikEtkinlik = c.getString(TAG_BASLIK);
                        adresEtkinlik = c.getString(TAG_ADRES);
                        baslikEtkinlik = html2text(baslikEtkinlik);
                        ayEtkinlik = c.getString(TAG_AY);
                        gunEtkinlik = c.getString(TAG_GUN);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loadEtkinlik.setVisibility(View.GONE);
            try {
                if (!baslikEtkinlik.isEmpty()) {
                    setBaslikEtkinlik.setText(gunEtkinlik + "\n" + months[Integer.parseInt(ayEtkinlik) - 1]);
                    setEtkinlik.setText(baslikEtkinlik);
                    cardEtkinlik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresEtkinlik);
                            intent.putExtra("baslik", baslikEtkinlik);
                            startActivity(intent);
                        }
                    });
                    setEtkinlik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), IcerikActivity.class);
                            intent.putExtra("adres", adresEtkinlik);
                            intent.putExtra("baslik", baslikEtkinlik);
                            startActivity(intent);
                        }
                    });
                } else {
                    emptyEtkinlik.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                emptyEtkinlik.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }

    private class getYemekJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url_yemek, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    liste = jsonObj.getJSONArray(TAG_LISTE);
                    for (int i = 0; i < 1; i++) {
                        JSONObject listeobject = liste.getJSONObject(i);
                        JSONArray yemekler = listeobject.getJSONArray(TAG_YEMEKLER);
                        for (int j = 0; j < yemekler.length(); j++) {
                            JSONObject yemeklerobject = yemekler.getJSONObject(j);
                            genelYemek = yemeklerobject.getString(TAG_YEMEK_ISIM);
                            cinsYemek = yemeklerobject.getString(TAG_CINS);
                            switch (cinsYemek) {
                                case "1":
                                    corbaYemek = genelYemek;
                                    break;
                                case "2":
                                    yemek1Yemek = genelYemek;
                                    break;
                                case "3":
                                    yemek2Yemek = genelYemek;
                                    break;
                                case "4":
                                    digerYemek = genelYemek;
                                    break;
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("jsonStrNull", "jsonStr variable is null.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                corbaText.setTextColor(getResources().getColor(R.color.BrushedBlue));
                yemek1Text.setTextColor(getResources().getColor(R.color.BrushedBlue));
                yemek2Text.setTextColor(getResources().getColor(R.color.BrushedBlue));
                digerText.setTextColor(getResources().getColor(R.color.BrushedBlue));
                corbaText.setText(corbaYemek);
                yemek1Text.setText(yemek1Yemek);
                yemek2Text.setText(yemek2Yemek);
                digerText.setText(digerYemek);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setFlipperImage(ArrayList<String> actorsList) {
        try {
            for (int i = 0; i < actorsList.size(); i++) {
                ImageView image = new ImageView(getActivity().getApplicationContext());
                Picasso.with(getActivity())
                        .load(actorsList.get(i))
                        .placeholder(R.drawable.loading_anasayfa)
                        .error(R.drawable.loading_anasayfa)
                        .into(image);
                viewFlipper.addView(image);
            }
        } catch (NullPointerException e) {
            Log.d("NPE", e.toString());
        }

    }

    private void AnimateandSlideShow() {
        try {
            viewFlipper.setInAnimation(getActivity(), R.anim.slide_in_from_right);
            viewFlipper.setOutAnimation(getActivity(), R.anim.slide_out_to_left);
            viewFlipper.setDisplayedChild(viewFlipper.getDisplayedChild() + 1);
        } catch (NullPointerException e) {
            Log.d("NPE", e.toString());
        }
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_web_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("web", "http://w3.beun.edu.tr/");
                AnaSayfa_Fragment.this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AnaSayfa_Fragment extends Fragment {


    private static String url_duyuru = "http://w3.beun.edu.tr/mobil-duyurular/";
    private static String url_haber = "http://w3.beun.edu.tr/mobil-haberler/";
    private static String url_etkinlik = "http://w3.beun.edu.tr/mobil-etkinlikler/";
    private static String url_slider = "http://w3.beun.edu.tr/mobil-slayt/";
    private static final String TAG_S1 = "s1";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";
    JSONArray s1 = null;

    ArrayList<String> imageList;
    ArrayList<String> imageLinkList;
    ViewFlipper viewFlipper;
    private float lastX;

    TextView emptyDuyuru, setDuyuru, loadDuyuru, emptyHaber, setHaber, loadHaber, emptyEtkinlik, setEtkinlik, loadEtkinlik;
    CardView cardDuyuru, cardHaber, cardEtkinlik;
    Button anaSayfaButton;

    String baslikDuyuru, adresDuyuru, baslikHaber, adresHaber, baslikEtkinlik, adresEtkinlik;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.ana_sayfa_title));

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

        anaSayfaButton = (Button) view.findViewById(R.id.ana_sayfa_button);
        anaSayfaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("web", "http://w3.beun.edu.tr/");
                AnaSayfa_Fragment.this.startActivity(intent);
            }
        });

        new getDuyuruJSON().execute();
        new getHaberJSON().execute();
        new getEtkinlikJSON().execute();


        viewFlipper = (ViewFlipper) view.findViewById(R.id.image_flipper);
        imageList = new ArrayList<String>();
        imageLinkList = new ArrayList<String>();
        new JSONImageSlider().execute(url_slider);
        FlipperEvents();

        return view;
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
        int period = 8000;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.post(mUpdateResults);
            }
        }, delay, period);

    }

    class JSONImageSlider extends AsyncTask<String, Void, Boolean> {

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
                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("s1");
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        //  Actors actor = new Actors();
                        imageList.add("http://w3.beun.edu.tr/dosyalar/" + object.getString("gorsel"));
                        imageLinkList.add(object.getString("adres"));
                        //   actor.setImage(object.getString("image"));
                        //   imageList.add(actor);
                    }
                    return true;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (result == false) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            } else {
                setFlipperImage(imageList);
            }
        }
    }


    private class getDuyuruJSON extends AsyncTask<Void, Void, Void> {

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
    }

    private class getHaberJSON extends AsyncTask<Void, Void, Void> {

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
    }


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

    private void setFlipperImage(ArrayList<String> actorsList) {

        for (int i = 0; i < actorsList.size(); i++) {
            ImageView image = new ImageView(getActivity().getApplicationContext());
// image.setBackgroundResource(res);
            Picasso.with(getActivity())
                    .load(actorsList.get(i))
                    .placeholder(R.drawable.ibnisina)
                    .error(R.drawable.anasayfatest)
                    .into(image);
            viewFlipper.addView(image);
        }
    }

    // method to show slide show
    private void AnimateandSlideShow() {
        viewFlipper.setInAnimation(getActivity(), R.anim.slide_in_from_right);
        viewFlipper.setOutAnimation(getActivity(), R.anim.slide_out_to_left);
        viewFlipper.setDisplayedChild(viewFlipper.getDisplayedChild() + 1);
    }


    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }


}
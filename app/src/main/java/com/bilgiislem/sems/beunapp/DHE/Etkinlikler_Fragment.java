package com.bilgiislem.sems.beunapp.DHE;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bilgiislem.sems.beunapp.DHE_Sources.DatePicker_Fragment;
import com.bilgiislem.sems.beunapp.DHE_Sources.IcerikActivity;
import com.bilgiislem.sems.beunapp.DHE_Sources.ServiceHandler;
import com.bilgiislem.sems.beunapp.MainActivity.MainActivity;
import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Etkinlikler_Fragment extends ListFragment {

    private static String url = "http://w3.beun.edu.tr/mobil-etkinlikler/";
    private static String urlbeun = "http://w3.beun.edu.tr/";
    ListView listView;
    Button tebutton;
    TextView emptyData, loadingData;

    private static final String TAG_S1 = "s1";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";
    JSONArray s1 = null;
    ArrayList<HashMap<String, String>> etkinlikList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dhe, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.etkinlik_title));

        loadingData = (TextView) view.findViewById(R.id.loading_data);
        emptyData = (TextView) view.findViewById(R.id.empty_data);
        emptyData.setText(R.string.empty_etkinlik);
        emptyData.setVisibility(View.GONE);

        tebutton = (Button) view.findViewById(R.id.tumbutton);
        tebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datePicker = new DatePicker_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("dhe", "etkinlik");
                datePicker.setArguments(bundle);
                datePicker.show(getFragmentManager(), "Date Picker");
            }
        });
        listView = new ListView(getActivity());
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        etkinlikList = new ArrayList<HashMap<String, String>>();
        listView = getListView();
        listView.setVisibility(View.GONE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String adres2 = etkinlikList.get(position).get("adres");
                String baslik2 = etkinlikList.get(position).get("baslik");
                Intent intent = new Intent(getActivity(), IcerikActivity.class);
                intent.putExtra("adres", adres2);
                intent.putExtra("baslik", baslik2);
                startActivity(intent);
            }
        });
        //new getEtkinlikJSON().execute();
        new getEtkinlikJsoup().execute();

        super.onActivityCreated(savedInstanceState);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    /*private class getEtkinlikJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    s1 = jsonObj.getJSONArray(TAG_S1);
                    for (int i = 0; i < s1.length(); i++) {
                        JSONObject c = s1.getJSONObject(i);
                        String baslik = c.getString(TAG_BASLIK);
                        String adres = c.getString(TAG_ADRES);
                        HashMap<String, String> contact = new HashMap<String, String>();
                        baslik = html2text(baslik);
                        contact.put(TAG_BASLIK, baslik);
                        contact.put(TAG_ADRES, adres);
                        etkinlikList.add(contact);
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            loadingData.setVisibility(View.GONE);
            if (etkinlikList.isEmpty()) {
                emptyData.setVisibility(View.VISIBLE);
            }
            try {
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), etkinlikList,
                        R.layout.item_listview, new String[]{TAG_BASLIK}, new int[]{R.id.news});
                setListAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {
                Log.d("NullPointer", "List adapter.");
            }
        }
    }
*/
    private class getEtkinlikJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(urlbeun).get();
                for (Element element : doc.select("ul[id=takvimul]")) {
                    HashMap<String, String> contact = new HashMap<String, String>();
                    contact.put(TAG_BASLIK, element.select("li > div > ol > li > ol > li > a").select("span[class=ai1ec-event-title]").text());
                    contact.put(TAG_ADRES, element.select("li > div > ol > li > ol > li").select("a").attr("abs:href"));
                    etkinlikList.add(contact);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (etkinlikList.isEmpty()) {
                emptyData.setVisibility(View.VISIBLE);
            }
            try {
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), etkinlikList,
                        R.layout.item_listview, new String[]{TAG_BASLIK}, new int[]{R.id.news});
                setListAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                loadingData.setVisibility(View.GONE);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
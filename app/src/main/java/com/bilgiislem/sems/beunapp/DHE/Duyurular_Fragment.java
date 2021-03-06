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
import java.util.List;

public class Duyurular_Fragment extends ListFragment {

    private static String url = "http://w3.beun.edu.tr/mobil-duyurular/";
    private static String urlbeun = "http://w3.beun.edu.tr/";
    ListView listView;
    Button tdbutton;
    TextView emptyData, loadingData;

    private static final String TAG_S1 = "s1";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";
    JSONArray s1 = null;
    ArrayList<HashMap<String, String>> duyuruList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dhe, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.duyuru_title));

        loadingData = (TextView) view.findViewById(R.id.loading_data);
        emptyData = (TextView) view.findViewById(R.id.empty_data);
        emptyData.setText(R.string.empty_duyuru);
        emptyData.setVisibility(View.GONE);

        tdbutton = (Button) view.findViewById(R.id.tumbutton);
        tdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datePicker = new DatePicker_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("dhe", "duyuru");
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
        duyuruList = new ArrayList<HashMap<String, String>>();
        listView = getListView();
        listView.setVisibility(View.GONE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String adres2 = duyuruList.get(position).get("adres");
                String baslik2 = duyuruList.get(position).get("baslik");
                Intent intent = new Intent(getActivity(), IcerikActivity.class);
                intent.putExtra("adres", adres2);
                intent.putExtra("baslik", baslik2);
                startActivity(intent);
            }
        });
        //new getDuyuruJSON().execute();
        new getDuyuruJsoup().execute();
        super.onActivityCreated(savedInstanceState);
    }

  /*  private class getDuyuruJSON extends AsyncTask<Void, Void, Void> {
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
                        duyuruList.add(contact);
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
            if (duyuruList.isEmpty()) {
                emptyData.setVisibility(View.VISIBLE);
            }
            try {
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), duyuruList,
                        R.layout.item_listview, new String[]{TAG_BASLIK}, new int[]{R.id.news});
                setListAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                loadingData.setVisibility(View.GONE);
            } catch (NullPointerException e) {
                Log.d("NullPointer", "In this try.");
            }
        }
    }
*/

    private class getDuyuruJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(urlbeun).get();
                for (Element element : doc.select("ul[class=dli]")) {
                    HashMap<String, String> contact = new HashMap<String, String>();
                    contact.put(TAG_BASLIK, element.text());
                    contact.put(TAG_ADRES, element.select("li a").attr("abs:href"));
                    duyuruList.add(contact);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (duyuruList.isEmpty()) {
                emptyData.setVisibility(View.VISIBLE);
            }
            try {
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), duyuruList,
                        R.layout.item_listview, new String[]{TAG_BASLIK}, new int[]{R.id.news});
                setListAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                loadingData.setVisibility(View.GONE);
            } catch (NullPointerException e) {
                Log.d("NullPointer", "onPostExecute");
            }
        }
    }


    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }


}
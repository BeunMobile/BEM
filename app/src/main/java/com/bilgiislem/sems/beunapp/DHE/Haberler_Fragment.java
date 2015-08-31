package com.bilgiislem.sems.beunapp.DHE;

import android.app.ProgressDialog;
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

import com.bilgiislem.sems.beunapp.DHE_Sources.DatePicker_Fragment;
import com.bilgiislem.sems.beunapp.DHE_Sources.IcerikActivity;
import com.bilgiislem.sems.beunapp.DHE_Sources.ServiceHandler;
import com.bilgiislem.sems.beunapp.MainAndWeb.MainActivity;
import com.bilgiislem.sems.beunapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;


public class Haberler_Fragment extends ListFragment {

    private ProgressDialog pDialog;
    private static String url = "http://w3.beun.edu.tr/mobil-haberler/";
    ListView listView;
    Button thbutton;
    private static final String TAG_S1 = "s1";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";
    JSONArray contacts = null;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dhe, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.haber_title));

        thbutton = (Button) view.findViewById(R.id.tumbutton);
        thbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment newFragment = new DatePicker_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("dhe", "haber");
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "Date Picker");
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


        contactList = new ArrayList<HashMap<String, String>>();
        listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String adres2 = contactList.get(position).get("adres");
                String baslik2 = contactList.get(position).get("baslik");
                Intent intent = new Intent(getActivity(), IcerikActivity.class);
                intent.putExtra("adres", adres2);
                intent.putExtra("baslik", baslik2);
                startActivity(intent);
            }
        });
        // Calling async task to get json
        new GetContacts().execute();
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setTitle(getActivity().getString(R.string.loading));
            pDialog.setMessage(getActivity().getString(R.string.waitfor));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    contacts = jsonObj.getJSONArray(TAG_S1);
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String baslik = c.getString(TAG_BASLIK);
                        String adres = c.getString(TAG_ADRES);
                        HashMap<String, String> contact = new HashMap<String, String>();
                        baslik = html2text(baslik);
                        contact.put(TAG_BASLIK, baslik);
                        contact.put(TAG_ADRES, adres);
                        contactList.add(contact);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), contactList,
                    R.layout.item_listview, new String[]{TAG_BASLIK}, new int[]{R.id.news});
            setListAdapter(adapter);
        }
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
package com.bilgiislem.sems.beunapp.Duyurular;


import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bilgiislem.sems.beunapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Menu2_Fragment_Duyurular extends ListFragment {

    private ProgressDialog pDialog;
    Button button;

    // URL to get contacts JSON
    private static String url = "http://w3.beun.edu.tr/mobil-duyurular/";
    ListView listView;
    // JSON Node names
    private static final String TAG_S1 = "s1";
    private static final String TAG_BASLIK = "baslik";
    private static final String TAG_ADRES = "adres";

    JSONArray contacts = null;


    ArrayList<HashMap<String, String>> contactList;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu2_layout_duyurular, container, false);
        button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        listView = new ListView(getActivity());


        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        contactList = new ArrayList<HashMap<String, String>>();

        listView = getListView();

        // Calling async task to get json
        new GetContacts().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "Yeah", Toast.LENGTH_SHORT).show();
            }
        });

        super.onActivityCreated(savedInstanceState);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getActivity().getString(R.string.waitfor));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_S1);
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String baslik = c.getString(TAG_BASLIK);
                        String adres = c.getString(TAG_ADRES);


                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put(TAG_BASLIK, baslik);
                        contact.put(TAG_ADRES, adres);

                        // adding contact to contact list
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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), contactList,
                    R.layout.json_items, new String[]{TAG_BASLIK, TAG_ADRES}, new int[]{R.id.name, R.id.adres});

            setListAdapter(adapter);
        }


    }


}
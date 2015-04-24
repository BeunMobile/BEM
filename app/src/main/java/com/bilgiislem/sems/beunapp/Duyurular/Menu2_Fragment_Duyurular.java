package com.bilgiislem.sems.beunapp.Duyurular;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgiislem.sems.beunapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Menu2_Fragment_Duyurular extends Fragment {

    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.menu2_layout_duyurular, container, false);
        new AsyncTaskParseJson().execute();
        return rootView;
    }

    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        final String TAG = "AsyncTaskParseJson.java";

        // set your json string url here
        String yourJsonStringUrl = "http://demo.codeofaninja.com/tutorials/json-example-with-php/index.php";

        // contacts JSONArray
        JSONArray dataJsonArr = null;

        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... arg0) {

            try {

                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get json string from url
                JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);

                // get the array of users
                dataJsonArr = json.getJSONArray("Users");

                // loop through all users
                for (int i = 0; i < dataJsonArr.length(); i++) {

                    JSONObject c = dataJsonArr.getJSONObject(i);

                    // Storing each json item in variable
                    String firstname = c.getString("firstname");
                    String lastname = c.getString("lastname");
                    String username = c.getString("username");

                    // show the values in our logcat
                    Log.e(TAG, "firstname: " + firstname
                            + ", lastname: " + lastname
                            + ", username: " + username);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {}
    }

}
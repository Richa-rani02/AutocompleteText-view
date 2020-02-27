package com.example.autocomplete;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

class JsonParse {
    double current_latitude,current_longitude;
    public JsonParse(){}
    public JsonParse(double current_latitude,double current_longitude) {
        this.current_latitude = current_latitude;
        this.current_longitude = current_longitude;
    }
    public List<SuggestGetSet> getParseJsonWCF(String sName) {
        List<SuggestGetSet> ListData = new ArrayList<SuggestGetSet>();
        try {

            String temp=sName.replace(" ", "%20");
            ////host your api on server and put link here/////////
            URL js = new URL("xxxxxxxxx?host_name="+temp);
            HttpURLConnection jc = (HttpURLConnection) js.openConnection();
            jc.setDoInput(true);
            jc.setRequestMethod("POST");

            /////put your authentication here//////

            String credentials = "xxxx" + ":" + "xxx";
            String auth = "Basic "
                    + Base64.encodeToString(credentials.getBytes(),
                    Base64.NO_WRAP);
            jc.setRequestProperty("Authorization", auth);

            /////put your api key here///////

            jc.setRequestProperty("X-API-KEY","xxxx");
            jc.setDoOutput(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            JSONObject jsonResponse = new JSONObject(line);
            JSONArray jsonArray = jsonResponse.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject r = jsonArray.getJSONObject(i);
                ListData.add(new SuggestGetSet(r.getString("id"),r.getString("host_name")));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;
    }
}

package com.example.gestionfaculte.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gestionfaculte.activity.ui.AjoutDemande.AjoutDemandeFragment;
import com.example.gestionfaculte.adapter.SpinnerAdapter;
import com.example.gestionfaculte.model.Service;
import com.example.gestionfaculte.parser.JSONParser;
import com.example.gestionfaculte.server.Server;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GetFactulteByUser extends AsyncTask<Void, Void, JSONObject> {

    Activity activity;

    TextView txt_faclute;




    public GetFactulteByUser(Activity activity, TextView txt_faclute) {
        this.activity = activity;
        this.txt_faclute = txt_faclute;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.


        JSONParser jsonParser = new JSONParser();
        String url = Server.IP + "get_etablissement_by_user.php";
        Log.e("url", url);
        ArrayList<NameValuePair> parametre = new ArrayList<NameValuePair>();

        SharedPreferences prefs = activity.getSharedPreferences(Server.PREF_SESSION, Context.MODE_PRIVATE);
        int id_user = prefs.getInt("id", 0);


        parametre.add(new BasicNameValuePair("txt_id_user", id_user + ""));

        JSONObject json = jsonParser.makeHttpRequest(url, "POST", parametre);
        Log.e("HTTP_RESPONSE", json.toString());


        // TODO: register the new account here.
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {


        try {

            boolean etat = json.getBoolean("type");
            if (etat) {

                Log.e("type", etat + "");


                String data = json.getString("data");
                Log.e("data", data);
                JSONArray ja = new JSONArray(data);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);

                    int id = jo.getInt("id");
                    String Libelle = jo.getString("libelle");


                    txt_faclute.setText(Libelle);
                    AjoutDemandeFragment.id_etablissement_selected = id;
                }


            } else {
                //  Toast.makeText(activity, " Valider votre login et mot de passe ", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("ERROR", e.getMessage());
        }


    }


}

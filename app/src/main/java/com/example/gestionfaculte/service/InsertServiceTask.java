package com.example.gestionfaculte.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gestionfaculte.R;
import com.example.gestionfaculte.activity.HomeActivity;
import com.example.gestionfaculte.activity.ui.AjoutDemande.AjoutDemandeFragment;
import com.example.gestionfaculte.parser.JSONParser;
import com.example.gestionfaculte.server.Server;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class InsertServiceTask extends AsyncTask<Void, Void, JSONObject> {

    Activity activity;
    int codeService;


    public InsertServiceTask(Activity activity, int codeService) {
        this.activity = activity;
        this.codeService = codeService;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        AjoutDemandeFragment.pb_valide.setVisibility(View.VISIBLE);
        AjoutDemandeFragment.btn_valider.setEnabled(false);
        AjoutDemandeFragment.back_btn_valide.setBackgroundColor(activity.getResources().getColor(R.color.colortxt));
        AjoutDemandeFragment.txt_valide.setText("Sauvegard encours ...");
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.


        JSONParser jsonParser = new JSONParser();
        String url = Server.IP + "insert_service.php";
        Log.e("url", url);
        ArrayList<NameValuePair> parametre = new ArrayList<NameValuePair>();

        SharedPreferences prefs = activity.getSharedPreferences(Server.PREF_SESSION, Context.MODE_PRIVATE);
        int id_user = prefs.getInt("id", 0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


        parametre.add(new BasicNameValuePair("txt_id_service", codeService + ""));
        parametre.add(new BasicNameValuePair("txt_id_user", id_user + ""));
        parametre.add(new BasicNameValuePair("txt_date_demande", sdf.format(new Date()) + ""));


        JSONObject json = jsonParser.makeHttpRequest(url, "POST", parametre);
        Log.e("HTTP_RESPONSE", json.toString());


        // TODO: register the new account here.
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {





        try {


            int  success = json.getInt("success");
            if (success==1) {

                AjoutDemandeFragment.pb_valide.setVisibility(View.INVISIBLE);
                AjoutDemandeFragment.btn_valider.setEnabled(false);
                AjoutDemandeFragment.back_btn_valide.setBackgroundColor(activity.getResources().getColor(R.color.green));
                AjoutDemandeFragment.txt_valide.setText("Sauvegard avec succès");

                Toast.makeText(activity, " Sauvegard avec succès", Toast.LENGTH_SHORT).show();

            } else {


                AjoutDemandeFragment.pb_valide.setVisibility(View.INVISIBLE);
                AjoutDemandeFragment.btn_valider.setEnabled(true);
                AjoutDemandeFragment.back_btn_valide.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                AjoutDemandeFragment.txt_valide.setText(" Vérifier vos données ");

                Toast.makeText(activity, " Vérifier vos données ", Toast.LENGTH_SHORT).show();


            }
        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("ERROR", e.getMessage());
        }


    }


}

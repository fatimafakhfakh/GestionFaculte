package com.example.gestionfaculte.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionfaculte.activity.ui.AjoutDemande.AjoutDemandeFragment;
import com.example.gestionfaculte.adapter.DemandeAdapterRV;
import com.example.gestionfaculte.model.ServiceUtilisateur;
import com.example.gestionfaculte.parser.JSONParser;
import com.example.gestionfaculte.server.Server;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListeDemandeService extends AsyncTask<Void, Void, JSONObject> {

    Activity activity;

   RecyclerView rv_list_demande ;
   ProgressBar pb;

   String  numeroEtat ;

   ArrayList<ServiceUtilisateur> listDemande ;

    public ListeDemandeService(Activity activity, RecyclerView rv_list_demande, ProgressBar pb, String numeroEtat) {
        this.activity = activity;
        this.rv_list_demande = rv_list_demande;
        this.pb = pb;
        this.numeroEtat = numeroEtat;
        listDemande= new ArrayList<>()  ;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.


        JSONParser jsonParser = new JSONParser();
        String url = Server.IP + "list_demande.php";
        Log.e("url", url);
        ArrayList<NameValuePair> parametre = new ArrayList<NameValuePair>();

        SharedPreferences prefs = activity.getSharedPreferences(Server.PREF_SESSION, Context.MODE_PRIVATE);
        int id_user = prefs.getInt("id", 0);


        parametre.add(new BasicNameValuePair("txt_num_etat", numeroEtat + ""));
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

                SimpleDateFormat  sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);


                    //        ,etat.Libelle  as
                    int id = jo.getInt("id");
                    int id_service = jo.getInt("id_service");
                    String libelle_service = jo.getString("libelle_service");
                    Date  date_demande = new Date()  ;
                    try {
                           date_demande = sdf.parse(jo.getString("date_demande"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String numeroEtat = jo.getString("numeroEtat");
                    String libelle_etat = jo.getString("libelle_etat");

                    ServiceUtilisateur serviceUtilisateur  = new ServiceUtilisateur(id ,id_service,libelle_service,date_demande ,numeroEtat ,libelle_etat );
                    listDemande.add(serviceUtilisateur);

                }


                DemandeAdapterRV adapterRV =new DemandeAdapterRV(activity ,listDemande) ;
                rv_list_demande.setAdapter(adapterRV);
                pb.setVisibility(View.INVISIBLE);

                //adapter here



            } else {

                DemandeAdapterRV adapterRV =new DemandeAdapterRV(activity ,listDemande) ;
                rv_list_demande.setAdapter(adapterRV);
                pb.setVisibility(View.INVISIBLE);
                //  Toast.makeText(activity, " Valider votre login et mot de passe ", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("ERROR", e.getMessage());
        }


    }


}

package com.example.gestionfaculte.service;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.gestionfaculte.R;
import com.example.gestionfaculte.activity.InscriptionActivity;
import com.example.gestionfaculte.activity.ui.AjoutDemande.AjoutDemandeFragment;
import com.example.gestionfaculte.adapter.SpinnerAdapter;
import com.example.gestionfaculte.model.Etablissement;
import com.example.gestionfaculte.model.Service;
import com.example.gestionfaculte.parser.JSONParser;
import com.example.gestionfaculte.server.Server;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListEtablissementTaskForSpinner extends AsyncTask<Void, Void, JSONObject> {

    Activity activity;

    Spinner sp_etablisement;

    String Origine;

    ArrayList<String> listLibelleEtab = new ArrayList<>();
    ArrayList<Etablissement> listEtablissement = new ArrayList<>();


    public ListEtablissementTaskForSpinner(Activity activity, Spinner sp_etablisement ) {
        this.activity = activity;
        this.sp_etablisement = sp_etablisement;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.


        JSONParser jsonParser = new JSONParser();
        String url = Server.IP + "list_etablissement.php";
        Log.e("url", url);
        ArrayList<NameValuePair> parametre = new ArrayList<NameValuePair>();


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


                listLibelleEtab.clear();
                listLibelleEtab.add("Selectionner votre Etablissement");

                listEtablissement.add(new Etablissement(0, "" ,""));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);


                    int id = jo.getInt("id");
                    String Libelle = jo.getString("libelle");
                    String adresse = jo.getString("adresse");

                    Etablissement etab  = new Etablissement(id, Libelle ,adresse);
                    listEtablissement.add(etab);
                    listLibelleEtab.add(Libelle);


                }

                // adapter

                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(activity, listLibelleEtab);
                sp_etablisement.setAdapter(spinnerAdapter);


                sp_etablisement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (position > 0) {

                            Etablissement EtabSelected = listEtablissement.get(position);
                            InscriptionActivity.CodeEtablissmentSelected = EtabSelected.getId();
                            Log.e("Secetur_selected", "" + EtabSelected.toString());

                            InscriptionActivity. btn_valider.setEnabled(true);
                            InscriptionActivity.  back_btn_valide.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary2));

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            } else {
                //  Toast.makeText(activity, " Valider votre login et mot de passe ", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("ERROR", e.getMessage());
        }


    }


}

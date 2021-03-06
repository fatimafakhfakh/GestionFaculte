package com.example.gestionfaculte.service;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


import com.example.gestionfaculte.R;
import com.example.gestionfaculte.activity.ui.AjoutDemande.AjoutDemandeFragment;
import com.example.gestionfaculte.adapter.SpinnerAdapter;
import com.example.gestionfaculte.model.Service;
import com.example.gestionfaculte.parser.JSONParser;
import com.example.gestionfaculte.server.Server;


import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListServiceTaskForSpinner extends AsyncTask<Void, Void, JSONObject> {

    Activity activity;

    Spinner sp_service;

    String Origine;

    ArrayList<String> listLibelleService = new ArrayList<>();
    ArrayList<Service> listService = new ArrayList<>();


    public ListServiceTaskForSpinner(Activity activity, Spinner sp_service ) {
        this.activity = activity;
        this.sp_service = sp_service;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.


        JSONParser jsonParser = new JSONParser();
        String url = Server.IP + "list_service.php";
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


                listLibelleService.clear();
                listLibelleService.add("Selectionner un Service");

                listService.add(new Service(0, ""));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);


                    int id = jo.getInt("id");
                    String Libelle = jo.getString("Libelle");


                    Service service = new Service(id, Libelle);
                    listService.add(service);
                    listLibelleService.add(Libelle);


                }

                // adapter

                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(activity, listLibelleService);
                sp_service.setAdapter(spinnerAdapter);


                sp_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (position > 0) {

                            Service serviceSelected = listService.get(position);
                            AjoutDemandeFragment.CodeServiceSelected = serviceSelected.getId();
                            Log.e("Secetur_selected", "" + serviceSelected.toString());
                            AjoutDemandeFragment. btn_valider.setEnabled(true);
                            AjoutDemandeFragment.  back_btn_valide.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary2));

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

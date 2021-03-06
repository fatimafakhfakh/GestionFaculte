package com.example.gestionfaculte.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gestionfaculte.R;
import com.example.gestionfaculte.activity.HomeActivity;
import com.example.gestionfaculte.activity.InscriptionActivity;
import com.example.gestionfaculte.activity.LoginActivity;

import com.example.gestionfaculte.parser.JSONParser;
import com.example.gestionfaculte.server.Server;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class InscriptionTask extends AsyncTask<Void, Void, JSONObject> {

    Activity activity;
    
 
String cin , nom  , prenom  ,login  , mot_de_passe   ;
int  id_etablissment  ;

    public InscriptionTask(Activity activity, String cin, String nom, String prenom, String login, String mot_de_passe, int id_etablissment) {
        this.activity = activity;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.mot_de_passe = mot_de_passe;
        this.id_etablissment = id_etablissment;
    }

    


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        InscriptionActivity.pb_valide.setVisibility(View.VISIBLE);
        InscriptionActivity.btn_valider.setEnabled(false);
        InscriptionActivity.back_btn_valide.setBackgroundColor(activity.getResources().getColor(R.color.colortxt));
        InscriptionActivity.txt_valide.setText("Inscription en cours ...");
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.


        JSONParser jsonParser = new JSONParser();
        String url = Server.IP + "inscription.php";
        Log.e("url", url);
        ArrayList<NameValuePair> parametre = new ArrayList<NameValuePair>();

        


        parametre.add(new BasicNameValuePair("txt_nom", nom + ""));
        parametre.add(new BasicNameValuePair("txt_prenom", prenom + ""));
        parametre.add(new BasicNameValuePair("txt_cin", cin+ ""));

        parametre.add(new BasicNameValuePair("txt_login", login + ""));
        parametre.add(new BasicNameValuePair("txt_mot_de_passe", mot_de_passe+ ""));
        parametre.add(new BasicNameValuePair("txt_id_etablissement", id_etablissment + ""));


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

                InscriptionActivity.pb_valide.setVisibility(View.INVISIBLE);
                InscriptionActivity.btn_valider.setEnabled(false);
                InscriptionActivity.back_btn_valide.setBackgroundColor(activity.getResources().getColor(R.color.green));
                InscriptionActivity.txt_valide.setText("Inscription avec succèes");


                Toast.makeText(activity, "Inscription avec succèes", Toast.LENGTH_SHORT).show();




                Intent toLogin = new Intent(activity, LoginActivity.class);
                activity.startActivity(toLogin);
                activity.finish();



            } else {


                InscriptionActivity.pb_valide.setVisibility(View.INVISIBLE);
                InscriptionActivity.btn_valider.setEnabled(true);
                InscriptionActivity.back_btn_valide.setBackgroundColor(activity.getResources().getColor(R.color.pink));
                InscriptionActivity.txt_valide.setText(" Vérifier vos données ");

                Toast.makeText(activity, " Vérifier vos données ", Toast.LENGTH_SHORT).show();


            }
        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("ERROR", e.getMessage());
        }


    }


}

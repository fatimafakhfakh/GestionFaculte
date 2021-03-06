package com.example.gestionfaculte.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


import com.example.gestionfaculte.activity.HomeActivity;
import com.example.gestionfaculte.parser.JSONParser;
import com.example.gestionfaculte.server.Server;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class LoginTask extends AsyncTask<Void, Void, JSONObject> {

    Activity activity;
    String login;
    String password;
    Button btn_login;


    public LoginTask(Activity activity, String login, String password, Button btn_login) {
        this.activity = activity;

        this.login = login;
        this.password = password;
        this.btn_login = btn_login;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        btn_login.setEnabled(false);
        btn_login.setText("connexion en cours ...");
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.


        JSONParser jsonParser = new JSONParser();
        String url = Server.IP + "login.php";
        Log.e("url", url);
        ArrayList<NameValuePair> parametre = new ArrayList<NameValuePair>();

        Log.e("param_task", "" + login + "  *  " + password);

        parametre.add(new BasicNameValuePair("txt_login", login + ""));
        parametre.add(new BasicNameValuePair("txt_password", password + ""));


        JSONObject json = jsonParser.makeHttpRequest(url, "POST", parametre);
        Log.e("HTTP_RESPONSE", json.toString());


        // TODO: register the new account here.
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {


        btn_login.setEnabled(true);


        try {


            boolean etat = json.getBoolean("type");
            if (etat) {

                btn_login.setText("connexion avec succèes");
                Log.e("type", etat + "");


                String data = json.getString("data");
                Log.e("data_user", data);
                JSONArray ja = new JSONArray(data);

                JSONObject jo = ja.getJSONObject(0);

                ///data  of  user

                int id = jo.getInt("id");
                String cin = jo.getString("cin");
                String nom = jo.getString("nom");
                String prenom = jo.getString("prenom");
                int id_etablissement = jo.getInt("id_etablissement");
                String login = jo.getString("login");
                String mot_de_passe = jo.getString("mot_de_passe");
                int autorisation = jo.getInt("autorisation");


                // sauvegard session
                SharedPreferences prefs = activity.getSharedPreferences(Server.PREF_SESSION, Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = prefs.edit();

                edt.putBoolean("etat_user", true);

                edt.putInt("id", id);
                edt.putString("cin", cin);
                edt.putString("nom", nom);
                edt.putString("prenom", prenom);
                edt.putInt("id_etablissement", id_etablissement);
                edt.putString("login", login);
                edt.putInt("autorisation", autorisation);

                edt.commit();

                Intent toAnnuaire = new Intent(activity, HomeActivity.class);
                activity.startActivity(toAnnuaire);
                activity.finish();


            } else {

                btn_login.setText("Ré-éssayer");
                Toast.makeText(activity, " Valider votre login et mot de passe ", Toast.LENGTH_SHORT).show();


            }
        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("ERROR", e.getMessage());
        }


    }


}

package com.example.gestionfaculte.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
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


    public LoginTask(Activity activity, String login, String password) {
        this.activity = activity;

        this.login = login;
        this.password = password;

    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.


        JSONParser jsonParser = new JSONParser();
        String url = Server.IP + "login.php";
        Log.e("url", url);
        ArrayList<NameValuePair> parametre = new ArrayList<NameValuePair>();

        Log.e      ("param_task",""+login+"  *  "+password)    ;
        
        parametre.add(new BasicNameValuePair("txt_login", login + ""));
        parametre.add(new BasicNameValuePair("txt_password", password + ""));


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
                Log.e("data_user", data);
                JSONArray ja = new JSONArray(data);

                JSONObject jo = ja.getJSONObject(0);

                ///data  of  user

                  int  id  = jo.getInt("id")       ;
                String cin = jo.getString("cin");
                String nom = jo.getString("nom");
                String prenom = jo.getString("prenom");
                String fonction = jo.getString("fonction");
                String email = jo.getString("email");
                String num_tel = jo.getString("num_tel");
                String adresse = jo.getString("adresse");
                String code_postal = jo.getString("codepostal");
                String ville = jo.getString("ville");



                      // sauvegard session 
                SharedPreferences prefs = activity.getSharedPreferences(Server.PREF_SESSION, Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = prefs.edit();

                edt.putBoolean("etat_user", true);
                edt.putString("cin", cin);
                edt.putString("nom", nom);
                edt.putString("prenom", prenom);
                edt.putString("fonction", fonction);
                edt.putString("adresse", adresse);
                edt.putString("code_postal", code_postal);
                edt.putString("ville", ville);


                edt.commit();




                    Intent  toAnnuaire  = new Intent(activity  , HomeActivity.class)       ;
                    activity.startActivity(toAnnuaire);
                    activity.finish();








            } else {
                Toast.makeText(activity, " Valider votre login et mot de passe ", Toast.LENGTH_SHORT).show();


            }
        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("ERROR", e.getMessage());
        }


    }


}

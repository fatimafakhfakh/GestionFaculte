package com.example.gestionfaculte.activity;

import androidx.appcompat.app.AppCompatActivity;
import  com.example.gestionfaculte.R;
import com.example.gestionfaculte.server.Server;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {


    private static int splash_screen = 3000;
    Animation topAnim;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        logo = findViewById(R.id.logo);
        logo.setAnimation(topAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs = getSharedPreferences(Server.PREF_SESSION, Context.MODE_PRIVATE);

                boolean etat_user = prefs.getBoolean("etat_user", false);


                if (etat_user) {

                    String fonction = prefs.getString("fonction", "false");


                    Intent toAnnuaire = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(toAnnuaire);
                    finish();


                } else if (!etat_user) {

                    Intent toLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(toLogin);
                    finish();


                }


            }
        }, splash_screen);

    }
}
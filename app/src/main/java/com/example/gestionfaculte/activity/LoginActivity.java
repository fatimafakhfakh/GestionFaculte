package com.example.gestionfaculte.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.gestionfaculte.R;
import com.example.gestionfaculte.service.LoginTask;
import com.google.android.material.textfield.TextInputLayout;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {



    private Button btn_login, button3;
    private TextInputLayout ed_login, ed_password;
    private TextView btn1,btn_ins;


    TextView btn_inscription ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);






        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn_login = findViewById(R.id.btn_login);
        btn_ins  = findViewById(R.id.btn_ins);
        ed_login = findViewById(R.id.ed_login);
        ed_password = findViewById(R.id.ed_password);


        btn_inscription = findViewById(R.id.btn_ins);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valideIdentifiant() | !valideMtp()) {
                    return;
                }
                String login = ed_login.getEditText().getText().toString();
                String mot_de_passe = ed_password.getEditText().getText().toString();

                Log.e      ("param",""+login+"  *  "+mot_de_passe)    ;


                LoginTask loginTask = new LoginTask(LoginActivity.this, login, mot_de_passe, btn_login);
                loginTask.execute();

            }
        });

        btn_ins.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {



                Intent i = new Intent(LoginActivity.this, InscriptionActivity.class);
                startActivity(i);
                finish();




            }

        });

    }



    private Boolean valideIdentifiant() {
        String val = ed_login.getEditText().getText().toString();
        if (val.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                    .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                    .setTitle("alert")

                    .setMessage("champ cin est vide")

                    .setNegativeButton("okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
            ed_login.setError("le champ ne peut pas être vide");
            return false;
        } else {
            ed_login.setError(null);

            return true;
        }
    }

    private Boolean valideMtp() {
        String val = ed_password.getEditText().getText().toString();
        if (val.isEmpty()) {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                    .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                    .setTitle("alert")
//set message
                    .setMessage("champ mot de passe est vide")
//s
//set negative button
                    .setNegativeButton("okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();

            ed_password.setError("le champ ne peut pas être vide");
            return false;
        } else {
            ed_password.setError(null);

            return true;
        }
    }

}
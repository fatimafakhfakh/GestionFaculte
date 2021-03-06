package com.example.gestionfaculte.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gestionfaculte.R;
import com.example.gestionfaculte.service.InscriptionTask;
import com.example.gestionfaculte.service.ListEtablissementTaskForSpinner;
import com.google.android.material.textfield.TextInputLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InscriptionActivity extends AppCompatActivity {


    private TextInputLayout ed_cin, ed_nom, ed_prenom, ed_login, ed_mot_de_passe;
    private Spinner sp_etablissement;

    public static CardView btn_valider;
    public static ConstraintLayout back_btn_valide;
    public static ProgressBar pb_valide;
    public static TextView txt_valide;


    public static int CodeEtablissmentSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        ed_cin = findViewById(R.id.ed_cin);
        ed_nom = findViewById(R.id.ed_nom);
        ed_prenom = findViewById(R.id.ed_prenom);
        ed_login = findViewById(R.id.ed_login);
        ed_mot_de_passe = findViewById(R.id.ed_mot_de_passe);
        sp_etablissement = findViewById(R.id.sp_etablissement);

        btn_valider = (CardView) findViewById(R.id.btn_valide);
        back_btn_valide = (ConstraintLayout) findViewById(R.id.back_btn_valide);
        pb_valide = findViewById(R.id.pb_valide);
        txt_valide = findViewById(R.id.txt_btn_valide);

        pb_valide.setVisibility(View.INVISIBLE);

        new ListEtablissementTaskForSpinner(this, sp_etablissement).execute();


        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valideCIN() | !valideNOM() | !validePRENOM() | !valideEtablissement() | !valideLogin() | !valideMP()) {
                    return;
                }


                String _cin = ed_cin.getEditText().getText().toString();
                String _nom = ed_nom.getEditText().getText().toString();
                String _prenom = ed_prenom.getEditText().getText().toString();
                String _login = ed_login.getEditText().getText().toString();
                String _mot_de_passe = ed_mot_de_passe.getEditText().getText().toString();
                int codeEtablissement = CodeEtablissmentSelected;


                new InscriptionTask( InscriptionActivity.this , _cin  ,_nom  ,_prenom ,_login  ,_mot_de_passe ,codeEtablissement).execute() ;

            }
        });


    }


    private Boolean valideCIN() {
        String val = ed_cin.getEditText().getText().toString();
        if (val.isEmpty()) {

         /*   AlertDialog alertDialog = new AlertDialog.Builder(this)

                    .setIcon(android.R.drawable.ic_dialog_alert)

                    .setTitle("alert")

                    .setMessage("champ mot de passe est vide")

                    .setNegativeButton("okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();*/

            ed_cin.setError("le champ ne peut pas être vide");
            return false;
        } else {
            ed_cin.setError(null);

            return true;
        }
    }


    private Boolean valideNOM() {
        String val = ed_nom.getEditText().getText().toString();
        if (val.isEmpty()) {

            ed_nom.setError("le champ ne peut pas être vide");
            return false;
        } else {
            ed_nom.setError(null);

            return true;
        }
    }

    private Boolean validePRENOM() {
        String val = ed_prenom.getEditText().getText().toString();
        if (val.isEmpty()) {

            ed_prenom.setError("le champ ne peut pas être vide");
            return false;
        } else {

            ed_prenom.setError(null);
            return true;
        }
    }

    private Boolean valideLogin() {
        String val = ed_login.getEditText().getText().toString();
        if (val.isEmpty()) {

            ed_login.setError("le champ ne peut pas être vide");
            return false;
        } else {
            ed_login.setError(null);

            return true;
        }
    }

    private Boolean valideMP() {
        String val = ed_mot_de_passe.getEditText().getText().toString();
        if (val.isEmpty()) {

            ed_mot_de_passe.setError("le champ ne peut pas être vide");
            return false;
        } else {
            ed_mot_de_passe.setError(null);

            return true;
        }
    }

    private Boolean valideEtablissement() {

        if (CodeEtablissmentSelected == 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(this)

                    .setIcon(android.R.drawable.ic_dialog_alert)

                    .setTitle("alert")

                    .setMessage("Vous devez selectionnez votre établissement ")

                    .setNegativeButton("okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what should happen when negative button is clicked
                            Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
            return false;
        } else {


            return true;
        }
    }

}
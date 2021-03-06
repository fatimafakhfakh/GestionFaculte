package com.example.gestionfaculte.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gestionfaculte.R;
import com.example.gestionfaculte.model.ServiceUtilisateur;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DemandeAdapterRV extends RecyclerView.Adapter<DemandeAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<ServiceUtilisateur> listDemande ;


    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    NumberFormat formatter = new DecimalFormat("0.000");


    public DemandeAdapterRV(Activity activity, ArrayList<ServiceUtilisateur> listDemande) {

        this.activity = activity;
        this.listDemande = listDemande;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ServiceUtilisateur demande = listDemande.get(position);

        holder.txt_liblle_service.setText(demande.getLibelle_service());
        holder.txt_etat_demande.setText(demande.getLibelle_etat() + "");
        holder.txt_date_demande.setText(df.format(demande.getDateDemande()) + "");



    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listDemande.size());
        return listDemande.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public TextView txt_liblle_service;
        public TextView txt_etat_demande;
        public TextView txt_date_demande;



        public ViewHolder(View itemView) {
            super(itemView);

            txt_liblle_service = (TextView) itemView.findViewById(R.id.txt_service);
            txt_etat_demande = (TextView) itemView.findViewById(R.id.txt_etat);
            txt_date_demande = (TextView) itemView.findViewById(R.id.txt_date_demande);


        }

    }

}

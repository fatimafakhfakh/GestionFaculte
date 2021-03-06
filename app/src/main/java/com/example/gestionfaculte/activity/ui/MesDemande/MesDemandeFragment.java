package com.example.gestionfaculte.activity.ui.MesDemande;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionfaculte.R;
import com.example.gestionfaculte.service.ListeDemandeService;

public class MesDemandeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    RadioGroup rg_etat ;
    RadioButton rb_en_cours ,rb_accepte ,rb_refuse  ;

    public  static String  CodeFonctionSlected ="" ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mes_demande, container, false);


        final RecyclerView rv_list_demande = root.findViewById(R.id.rv_list_demande);
        final ProgressBar progressBar = root.findViewById(R.id.progress_bar);
        rv_list_demande.setHasFixedSize(true);
        rv_list_demande.setLayoutManager( new LinearLayoutManager( getActivity() ) );


        rb_en_cours = root.findViewById(R.id.rb_en_cours)  ;
        rb_accepte = root.findViewById(R.id.rb_accepte)  ;
        rb_refuse = root.findViewById(R.id.rb_refuse)  ;


        new ListeDemandeService(getActivity() , rv_list_demande , progressBar ,"E01").execute() ;


        rb_en_cours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListeDemandeService(getActivity() , rv_list_demande , progressBar ,"E01").execute() ;
            }
        });

        rb_accepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListeDemandeService(getActivity() , rv_list_demande , progressBar ,"E02").execute() ;
            }
        });

        rb_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListeDemandeService(getActivity() , rv_list_demande , progressBar ,"E03").execute() ;
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });




        return root;
    }
}
package com.example.gestionfaculte.activity.ui.AjoutDemande;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gestionfaculte.R;
import com.example.gestionfaculte.service.GetFactulteByUser;
import com.example.gestionfaculte.service.InsertServiceTask;
import com.example.gestionfaculte.service.ListServiceTaskForSpinner;

public class AjoutDemandeFragment extends Fragment {

    public static int id_etablissement_selected = 0;
    public static int CodeServiceSelected = 0;
    private DashboardViewModel dashboardViewModel;

    //get_etablissement_by_user

    Spinner sp_service;
    TextView txt_faculte;


    public static CardView btn_valider;
    public static ConstraintLayout back_btn_valide;
    public static ProgressBar pb_valide;
    public static TextView txt_valide;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ajout_demande, container, false);
        sp_service = (Spinner) root.findViewById(R.id.sp_service);
        txt_faculte = (TextView) root.findViewById(R.id.txt_faculte);

        btn_valider = (CardView) root.findViewById(R.id.btn_valide);
        back_btn_valide = (ConstraintLayout) root.findViewById(R.id.back_btn_valide);
        pb_valide = root.findViewById(R.id.pb_valide);
        txt_valide = root.findViewById(R.id.txt_btn_valide);


        new GetFactulteByUser(getActivity(), txt_faculte).execute();
        new ListServiceTaskForSpinner(getActivity(), sp_service).execute();


        pb_valide.setVisibility(View.INVISIBLE);
        btn_valider.setEnabled(false);
        back_btn_valide.setBackgroundColor(getResources().getColor(R.color.gris));

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new InsertServiceTask(getActivity(), CodeServiceSelected).execute();

            }
        });


        return root;
    }
}
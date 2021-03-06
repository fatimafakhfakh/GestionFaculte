package com.example.gestionfaculte.activity.ui.compte;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gestionfaculte.R;
import com.example.gestionfaculte.activity.SplashScreenActivity;
import com.example.gestionfaculte.server.Server;

public class CompteFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_compte, container, false);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        final CardView btn_disconnect = root.findViewById(R.id.btn_disconnect);
        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences pref = getActivity().getSharedPreferences(Server.PREF_SESSION, Context.MODE_PRIVATE);
                SharedPreferences.Editor edt  = pref.edit();
                edt.putBoolean("etat_user",false);
                edt.commit();
                getActivity().   finish();
                Toast.makeText(getActivity() ,"Déconnexion ..", Toast.LENGTH_LONG).show();
                Intent inte = new Intent( getActivity().getApplicationContext(), SplashScreenActivity.class);
                startActivity(inte);



            }
        });


        return root;
    }
}
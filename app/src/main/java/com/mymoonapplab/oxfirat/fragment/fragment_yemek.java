package com.mymoonapplab.oxfirat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.async_task.async_yemekhane;
import com.mymoonapplab.oxfirat.interfacee.interface_yemekhane;

import java.util.ArrayList;

public class fragment_yemek extends Fragment implements interface_yemekhane {

    private TextView txt_liste,txt_menu,txt_tarih;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_yemekhane,container,false);

        txt_liste=rootView.findViewById(R.id.yemek_listesi);
        txt_menu=rootView.findViewById(R.id.menu);
        txt_tarih=rootView.findViewById(R.id.textView_tarihh);
        progressBar=rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        new async_yemekhane(this).execute(getResources().getString(R.string.yemekhane_sitesi));

        return rootView;
    }


    @Override
    public void bilgi_aktar(ArrayList<String> yemek_listesi, String tarih) {
        txt_tarih.setText(tarih);
        txt_menu.setText(getResources().getString(R.string.menu));


        StringBuilder liste= new StringBuilder();

        for (int i=0;i<yemek_listesi.size();i++){
            liste.append(yemek_listesi.get(i)).append("\n");
        }
        txt_liste.setText(liste.toString());
        progressBar.setVisibility(View.INVISIBLE);

    }
}

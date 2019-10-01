package com.mymoonapplab.oxfirat.yemekhane;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mymoonapplab.oxfirat.R;

import java.util.ArrayList;

public class fragment_yemek extends Fragment implements interface_yemekhane {

    private TextView txt_liste,txt_menu,txt_tarih;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_yemekhane,container,false);

        txt_liste=rootView.findViewById(R.id.yemek_listesi);
        txt_menu=rootView.findViewById(R.id.menu);
        txt_tarih=rootView.findViewById(R.id.textView_tarihh);

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
    }
}

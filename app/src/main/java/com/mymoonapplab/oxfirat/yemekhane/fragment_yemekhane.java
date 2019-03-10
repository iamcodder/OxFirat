package com.mymoonapplab.oxfirat.yemekhane;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymoonapplab.oxfirat.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class fragment_yemekhane extends Fragment {


    private TextView textView_liste;
    private TextView textView_menu;
    private TextView textView_tarih;
    private AVLoadingIndicatorView progressBar_pacman;

    private WaveSwipeRefreshLayout swipeRefresh_damla;

    private asyncTask_yemekCek asynTask_yemekCek_object = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_yemekhane, null);

        asynTask_yemekCek_object = new asyncTask_yemekCek();
        asynTask_yemekCek_object.execute();

        progressBar_pacman = rootview.findViewById(R.id.fragmentyemekhane_progress_avi);

        textView_liste = rootview.findViewById(R.id.yemek_listesi);
        textView_menu = rootview.findViewById(R.id.menu);
        textView_tarih = rootview.findViewById(R.id.textView_tarihh);

        swipeRefresh_damla = rootview.findViewById(R.id.fragment_yemekhane_waveswipe);
        swipeRefresh_damla.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asyncTask_yemekCek().execute();
            }
        });


        return rootview;
    }


    @SuppressLint("StaticFieldLeak")
    private class asyncTask_yemekCek extends AsyncTask<Void, Void, Void> {

        private String tarih;
        private String menu;
        private String yemeklerin_listesi;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tarih = null;
            menu = null;
            yemeklerin_listesi = "";
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document document = Jsoup.connect(getResources().getString(R.string.yemekhane_sitesi)).get();

                Elements elementsListe = document.select("div[class=views-field views-field-body]").select("p");

                for (int i = 0; i < elementsListe.size(); i++) {
                    yemeklerin_listesi = yemeklerin_listesi + (elementsListe.get(i).text() + "\n");
                }

                Elements elementsMenu = document.select("div[class=views-field views-field-title]");
                menu = elementsMenu.get(0).text();

                Elements elementsTarih = document.select("span[class=field-content");
                tarih = elementsTarih.get(1).text();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar_pacman.smoothToHide();
            textView_menu.setText(menu);
            textView_liste.setText(yemeklerin_listesi);
            textView_tarih.setText(tarih);

            if (swipeRefresh_damla.isRefreshing()) {
                swipeRefresh_damla.setRefreshing(false);
            }

            if (textView_menu.getText() == "") {
                textView_liste.setText(R.string.yemekhane_cekilemedi);
            }

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (asynTask_yemekCek_object != null && asynTask_yemekCek_object.cancel(true)) {
            asynTask_yemekCek_object = null;
        }
    }
}

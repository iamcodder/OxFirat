package com.example.iamcodder.androidd.yemekhane;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.iamcodder.androidd.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class fragment_yemekhane extends Fragment {


    private ProgressBar bar;
    private TextView textView_liste;
    private TextView textView_menu;
    private TextView textView_tarih;

    private WaveSwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview= inflater.inflate(R.layout.fragment_yemekhane, container, false);

        new yemekhane().execute();

        bar=rootview.findViewById(R.id.yemekhane_progressbar);

        textView_liste =rootview.findViewById(R.id.yemek_listesi);
        textView_menu=rootview.findViewById(R.id.menu);
        textView_tarih=rootview.findViewById(R.id.textView_tarihh);

        swipeRefreshLayout=rootview.findViewById(R.id.fragment_yemekhane_waveswipe);
        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new yemekhane().execute();
            }
        });

        return rootview;
    }


    @SuppressLint("StaticFieldLeak")
    private class yemekhane extends AsyncTask<Void,Void,Void> {

        private String tarih;
        private String menu;
        private String yemeklerin_listesi;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tarih=null;
            menu=null;
            yemeklerin_listesi="";
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document document=Jsoup.connect("http://uevi.firat.edu.tr").get();

                Elements elementsListe=document.select("div[class=views-field views-field-body]").select("p");

                for (int i=0;i<elementsListe.size();i++){
                    yemeklerin_listesi = yemeklerin_listesi + (elementsListe.get(i).text() + "\n");
                }

                Elements elementsMenu=document.select("div[class=views-field views-field-title]");
                menu=elementsMenu.get(0).text();

                Elements elementsTarih=document.select("span[class=field-content");
                tarih=elementsTarih.get(1).text();



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            bar.setVisibility(View.INVISIBLE);
            textView_menu.setText(menu);
            textView_liste.setText(yemeklerin_listesi);
            textView_tarih.setText(tarih);

            swipeRefreshLayout.setRefreshing(false);

        }
    }
}

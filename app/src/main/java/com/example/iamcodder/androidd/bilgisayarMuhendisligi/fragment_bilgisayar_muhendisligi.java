package com.example.iamcodder.androidd.bilgisayarMuhendisligi;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.iamcodder.androidd.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class fragment_bilgisayar_muhendisligi extends Fragment {


    private final String BILGISAYAR_MUHENDISLIGI_URL = "http://bil.muh.firat.edu.tr";

    private ArrayList<String> haberler_basligi;
    private ArrayList<String> haberler_linki;

    private ArrayList<String> duyurular_icerigi;
    private ArrayList<String> duyurular_linki;

    private RecyclerView recyclerView;
    private bilgisayar_muhendisligi_adapter adapter;
    private ProgressBar bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        new duyurular().execute();


        View rootView = inflater.inflate(R.layout.fragment_bilgisayar_muhendisligi, container, false);

        bar=rootView.findViewById(R.id.bilgisayar_muhendisligi_bar);
        recyclerView = rootView.findViewById(R.id.fragment_bilgisayar_muhendisligi_recycleView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        adapter = new bilgisayar_muhendisligi_adapter(getContext(), haberler_basligi, haberler_linki);
        recyclerView.setAdapter(adapter);

        return rootView;


    }


    private class duyurular extends AsyncTask<Void, Void, Void> {


        private Document document;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            haberler_basligi = new ArrayList<>();
            haberler_linki = new ArrayList<>();

            duyurular_icerigi = new ArrayList<>();
            duyurular_linki = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                document = Jsoup.connect(BILGISAYAR_MUHENDISLIGI_URL).get();
                elementleri_listeye_ekleme();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void elementleri_listeye_ekleme() {
            Elements duyuruHaber1 = document.select("div[class=views-row views-row-1 views-row-odd views-row-first]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuruHaber2 = document.select("div[class=views-row views-row-2 views-row-even]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuruHaber3 = document.select("div[class=views-row views-row-3 views-row-odd]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuruHaber4 = document.select("div[class=views-row views-row-4 views-row-even]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuruHaber5 = document.select("div[class=views-row views-row-5 views-row-odd views-row-last]").select("div[class=views-field views-field-title]").select("span[class=field-content]");

            Elements duyuru5 = document.select("div[class=views-row views-row-5 views-row-odd]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuru6 = document.select("div[class=views-row views-row-6 views-row-even]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuru7 = document.select("div[class=views-row views-row-7 views-row-odd views-row-last]").select("div[class=views-field views-field-title]").select("span[class=field-content]");



            haberler_basligi.add(duyuruHaber1.get(0).text());
            haberler_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber1.get(0).select("a").attr("href"));
            haberler_basligi.add(duyuruHaber2.get(0).text());
            haberler_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber2.get(0).select("a").attr("href"));
            haberler_basligi.add(duyuruHaber3.get(0).text());
            haberler_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber3.get(0).select("a").attr("href"));
            haberler_basligi.add(duyuruHaber4.get(0).text());
            haberler_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber4.get(0).select("a").attr("href"));
            haberler_basligi.add(duyuruHaber5.get(0).text());
            haberler_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber5.get(0).select("a").attr("href"));


                duyurular_icerigi.add(duyuruHaber1.get(1).text());
                duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber1.get(1).select("a").attr("href"));
            duyurular_icerigi.add(duyuruHaber2.get(1).text());
            duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber2.get(1).select("a").attr("href"));
            duyurular_icerigi.add(duyuruHaber3.get(1).text());
            duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber3.get(1).select("a").attr("href"));
            duyurular_icerigi.add(duyuruHaber4.get(1).text());
            duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuruHaber4.get(1).select("a").attr("href"));


            duyurular_icerigi.add(duyuru5.get(0).text());
            duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuru5.get(0).select("a").attr("href"));
            duyurular_icerigi.add(duyuru6.get(0).text());
            duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuru6.get(0).select("a").attr("href"));
            duyurular_icerigi.add(duyuru7.get(0).text());
            duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL + duyuru7.get(0).select("a").attr("href"));



        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            bar.setVisibility(View.INVISIBLE);

            recyclerView.setAdapter(adapter);

            recyclerView.invalidate();


        }
    }

}


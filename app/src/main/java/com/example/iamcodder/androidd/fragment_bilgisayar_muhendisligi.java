package com.example.iamcodder.androidd;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class fragment_bilgisayar_muhendisligi extends Fragment {


    private final String BILGISAYAR_MUHENDISLIGI_URL="http://bil.muh.firat.edu.tr";

    ArrayList<String> haberler_basligi;
    ArrayList<String > haberler_linki;

    ArrayList<String> duyurular_icerigi;
    ArrayList<String > duyurular_linki;

    private RecyclerView recyclerView;
    bilgisayar_muhendisligi_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new duyurular().execute();
        View rootView= inflater.inflate(R.layout.fragment_fragment_bilgisayar_muhendisligi, container, false);

        recyclerView=rootView.findViewById(R.id.fragment_bilgisayar_muhendisligi_recycleView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        adapter=new bilgisayar_muhendisligi_adapter(getContext(),haberler_basligi,haberler_linki);



        return rootView;


    }



    private class duyurular extends AsyncTask<Void,Void,Void>{


        private Document document;
        private ArrayList<Elements> elements_haberler_listesi;
        private ArrayList<Elements> elements_duyuru_listesi;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            elements_haberler_listesi =new ArrayList<>();
            elements_duyuru_listesi =new ArrayList<>();

            haberler_basligi=new ArrayList<>();
            haberler_linki=new ArrayList<>();

            duyurular_icerigi=new ArrayList<>();
            duyurular_linki=new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                document=Jsoup.connect(BILGISAYAR_MUHENDISLIGI_URL).get();
                elementleri_listeye_ekleme();



            } catch (IOException e) {
                e.printStackTrace();
            }return null;
        }

        private void elementleri_listeye_ekleme(){
            Elements duyuruHaber1 =document.select("div[class=views-row views-row-1 views-row-odd views-row-first]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuruHaber2=document.select("div[class=views-row views-row-2 views-row-even]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuruHaber3=document.select("div[class=views-row views-row-3 views-row-odd]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuruHaber4=document.select("div[class=views-row views-row-4 views-row-even]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuruHbaer5=document.select("div[class=views-row views-row-5 views-row-odd views-row-last]").select("div[class=views-field views-field-title]").select("span[class=field-content]");

            Elements duyuru5=document.select("div[class=views-row views-row-5 views-row-odd]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuru6=document.select("div[class=views-row views-row-6 views-row-even]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            Elements duyuru7=document.select("div[class=views-row views-row-7 views-row-odd views-row-last]").select("div[class=views-field views-field-title]").select("span[class=field-content]");
            
            elements_haberler_listesi.add(duyuruHaber1);
            elements_haberler_listesi.add(duyuruHaber2);
            elements_haberler_listesi.add(duyuruHaber3);
            elements_haberler_listesi.add(duyuruHaber4);
            elements_haberler_listesi.add(duyuruHbaer5);

            elements_duyuru_listesi.add(duyuruHaber1);
            elements_duyuru_listesi.add(duyuruHaber2);
            elements_duyuru_listesi.add(duyuruHaber3);
            elements_duyuru_listesi.add(duyuruHaber4);
            elements_duyuru_listesi.add(duyuru5);
            elements_duyuru_listesi.add(duyuru6);
            elements_duyuru_listesi.add(duyuru7);        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            for (int j = 0; j< elements_haberler_listesi.size(); j++){

                haberler_basligi.add(elements_haberler_listesi.get(j).get(0).text());
                haberler_linki.add(BILGISAYAR_MUHENDISLIGI_URL+ elements_haberler_listesi.get(j).get(0).select("a").attr("href"));

            }

            for (int i = 0; i< elements_haberler_listesi.size()-1; i++){
                duyurular_icerigi.add(elements_duyuru_listesi.get(i).get(1).text());
                duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL+ elements_duyuru_listesi.get(i).get(1).select("a").attr("href"));
            }

            for (int j = 4; j< 7; j++){
                duyurular_icerigi.add(elements_duyuru_listesi.get(j).get(0).text());
                duyurular_linki.add(BILGISAYAR_MUHENDISLIGI_URL+ elements_duyuru_listesi.get(j).get(0).select("a").attr("href"));
            }

            recyclerView.setAdapter(adapter);

            recyclerView.invalidate();


        }
    }

}


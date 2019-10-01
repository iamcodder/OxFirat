package com.mymoonapplab.oxfirat.yemekhane;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class async_yemekhane extends AsyncTask<String,Void,Void> {

    private String tarih;
    private ArrayList<String> liste_yemek;
    private interface_yemekhane interface_yemekhane;

    public async_yemekhane(com.mymoonapplab.oxfirat.yemekhane.interface_yemekhane interface_yemekhane) {
        this.interface_yemekhane = interface_yemekhane;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        liste_yemek=new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... url) {

        try {

            Document document=Jsoup.connect(url[0]).get();

            Elements elementsListe=document.select("div[class=views-field views-field-body]").select("p");

            for (int i=0;i<elementsListe.size();i++){
                if(elementsListe.get(i)!=null && !elementsListe.get(i).text().equals("")){
                    liste_yemek.add(elementsListe.get(i).text());
                }


            }

            Elements elementsTarih=document.select("span[class=field-content");
            tarih = elementsTarih.get(1).text();

        }
        catch (Exception e){
            Log.d("Error : ", Objects.requireNonNull(e.getLocalizedMessage()));
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        interface_yemekhane.bilgi_aktar(liste_yemek,tarih);


    }
}

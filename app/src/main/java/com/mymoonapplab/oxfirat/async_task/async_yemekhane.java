package com.mymoonapplab.oxfirat.async_task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mymoonapplab.oxfirat.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class async_yemekhane extends AsyncTask<String,Void,Void> {

    private String tarih;
    private ArrayList<String> liste_yemek;
    private com.mymoonapplab.oxfirat.interfacee.interface_yemekhane interface_yemekhane;
    private Context mContext;

    public async_yemekhane(com.mymoonapplab.oxfirat.interfacee.interface_yemekhane interface_yemekhane,Context mContext) {
        this.interface_yemekhane = interface_yemekhane;
        this.mContext=mContext;
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
                Log.d("Error : ", "");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        if(!liste_yemek.isEmpty() && !tarih.isEmpty()){
            interface_yemekhane.bilgi_aktar(liste_yemek,tarih);
        }

        else {
            new async_yemekhane(interface_yemekhane,mContext).execute(mContext.getResources().getString(R.string.yemekhane_sitesi));
            Toast.makeText(mContext,"İnternet hızınız yavaş",Toast.LENGTH_SHORT).show();

        }

    }
}

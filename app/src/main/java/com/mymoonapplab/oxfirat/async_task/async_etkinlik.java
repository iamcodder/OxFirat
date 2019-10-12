package com.mymoonapplab.oxfirat.async_task;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class async_etkinlik extends AsyncTask<String ,Void ,Void> {

    ArrayList<String> list_baslik,list_tarih,list_link;

    com.mymoonapplab.oxfirat.interfacee.interface_etkinlik interface_etkinlik;

    public async_etkinlik(com.mymoonapplab.oxfirat.interfacee.interface_etkinlik interface_etkinlik) {
        this.interface_etkinlik = interface_etkinlik;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        list_baslik=new ArrayList<>();
        list_link=new ArrayList<>();
        list_tarih=new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... strings) {

        try {

            String site_etkinlik=strings[0];
            String site_okul=strings[1];

            Document document= Jsoup.connect(site_etkinlik).get();
            Elements elements_etkn=document.select("div[class=banner col-xs-12 col-sm-4 col-lg-3");

            for (int i=0;i<elements_etkn.size();i++){

                list_baslik.add(elements_etkn.get(i).select("div[class=bottom").text());
                list_tarih.add(elements_etkn.get(i).select("span[class=day]").text());
                list_link.add(site_okul + elements_etkn.get(i).select("a").attr("href"));

            }


        }
        catch (Exception e){
            Log.d("Sülo","Error etk");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        if(!list_baslik.isEmpty() && !list_tarih.isEmpty() && !list_link.isEmpty()){
            interface_etkinlik.etkinlik_bilgisi_aktarimi(list_baslik,list_tarih,list_link);
        }
        else {
            new async_etkinlik(interface_etkinlik).execute();
        }
    }
}

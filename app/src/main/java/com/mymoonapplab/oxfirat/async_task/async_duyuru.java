package com.mymoonapplab.oxfirat.async_task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.fragment.fragment_duyurular;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class async_duyuru extends AsyncTask<String,Void,Void> {

    private ArrayList<String> list_etkinlik_baslik,list_etkinlik_tarih,list_etkinlik_link;
    private com.mymoonapplab.oxfirat.interfacee.interface_duyurular interface_duyurular;
    private Context mContext;

    public async_duyuru(com.mymoonapplab.oxfirat.interfacee.interface_duyurular interface_duyurular,Context mContext) {
        this.interface_duyurular = interface_duyurular;
        this.mContext=mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        list_etkinlik_baslik=new ArrayList<>();
        list_etkinlik_link=new ArrayList<>();
        list_etkinlik_tarih=new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... strings) {

        String site_okul=strings[0];
        String site_duyuru=strings[1];

        try {

            Document document= Jsoup.connect(site_duyuru + fragment_duyurular.sayfa_sayisi).get();
            Elements elements_duyuru=document.select("div[class=banner col-xs-12 col-sm-4 col-lg-3]");

            for (int i = 0; i < elements_duyuru.size(); i++) {

                list_etkinlik_baslik.add(elements_duyuru.get(i).select("div[class=top]").text());
                list_etkinlik_tarih.add(elements_duyuru.get(i).select("span[class=day]").text());
                list_etkinlik_link.add((site_okul) + elements_duyuru.get(i).select("a").attr("href"));

            }

        }

        catch (Exception e){
            Log.d("Sülo : ", "error duy");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);



        if(!list_etkinlik_baslik.isEmpty() && !list_etkinlik_tarih.isEmpty() && !list_etkinlik_link.isEmpty()){
            interface_duyurular.duyuru_bilgisi_aktarimi(list_etkinlik_baslik,list_etkinlik_tarih,list_etkinlik_link);
        }
        else {
            new async_duyuru(interface_duyurular,mContext).execute(mContext.getResources().getString(R.string.okul_sitesi),
                    mContext.getResources().getString(R.string.duyurular_sitesi));
            Toast.makeText(mContext,"İnternet hızınız yavaş",Toast.LENGTH_SHORT).show();

        }
    }
}

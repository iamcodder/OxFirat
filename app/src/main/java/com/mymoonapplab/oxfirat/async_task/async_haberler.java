package com.mymoonapplab.oxfirat.async_task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.fragment.fragment_haberler;
import com.mymoonapplab.oxfirat.interfacee.interface_haberler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class async_haberler extends AsyncTask<String,Void,Void> {

    ArrayList<String> list_haber_basligi, list_haber_resmi, list_haber_linki;
    private com.mymoonapplab.oxfirat.interfacee.interface_haberler interface_haberler;
    private Context mContext;

    public async_haberler(interface_haberler interface_haberler,Context mContext){
        this.interface_haberler=interface_haberler;
        this.mContext=mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        list_haber_basligi=new ArrayList<>();
        list_haber_linki=new ArrayList<>();
        list_haber_resmi=new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... strings) {

        //strings dizisinden okul sitesi ve haber sitesini alacağız
        String okul_sitesi = strings[0];
        String haber_sitesi = strings[1];

        Elements elements_haber;

        try {
            Document document = Jsoup.connect(haber_sitesi + fragment_haberler.sayi_sayfa).get();
            elements_haber = document.select("div[class=row all-news]").select("div[class=banner col-xs-12 col-sm-4 col-md-4 col-lg-3]");

            for (int i = 0; i < elements_haber.size(); i++) {

                list_haber_basligi.add(elements_haber.get(i).select("div[class=top]").text());
                list_haber_linki.add(okul_sitesi + elements_haber.get(i).select("a").attr("href"));

                String[] parcalama = elements_haber.get(i).select("div").attr("style").split("'");

                list_haber_resmi.add(parcalama[1]);
            }
        }
        catch(Exception e){
            }

            return null;
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        if(!list_haber_basligi.isEmpty() && !list_haber_resmi.isEmpty() && !list_haber_linki.isEmpty()){
            interface_haberler.haber_bilgisi_aktarimi(list_haber_basligi,list_haber_resmi,list_haber_linki);
        }
        else {
            new async_haberler(interface_haberler,mContext).execute(mContext.getResources().getString(R.string.okul_sitesi),
                    mContext.getResources().getString(R.string.haber_sitesi));
            Toast.makeText(mContext,"İnternet hızınız yavaş",Toast.LENGTH_SHORT).show();
        }
    }
}

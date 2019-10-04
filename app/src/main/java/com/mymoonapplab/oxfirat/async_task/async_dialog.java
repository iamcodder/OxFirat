package com.mymoonapplab.oxfirat.async_task;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class async_dialog extends AsyncTask<String,Void,String> {

    private Elements elements;
    private String haber_basligi;
    private String haber_tarihi;
    private String haber_icerigi;
    private String haberdeki_link;

    private int haberdeki_resim_sayisi;
    private int paragraf_sayisi;

    private ArrayList<String> resim_linkleri;

    private int tablo_sayisi;

    private com.mymoonapplab.oxfirat.interfacee.interface_dialogbox interface_dialogbox;

    public async_dialog(com.mymoonapplab.oxfirat.interfacee.interface_dialogbox interface_dialogbox) {
        this.interface_dialogbox = interface_dialogbox;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        haber_icerigi = "";
        resim_linkleri = new ArrayList<>();

    }

    @Override
    protected String doInBackground(String... strings) {

        //strings[0]=URL_LINKI;
        //strings[1]=okul_sitesi;

        try {
            Document document = Jsoup.connect(strings[0]).get();

            elements = document.select("div[class=col-xs-12 col-md-8 lm-md-t7 lm-xs-t6 lm-xs-b6 searchMain]");

            haber_basligi = elements.select("div[class=row]").text();

            haber_basligi = haber_basligi.trim();

            //başlığı çekince sonunda "A" harfi oluyordu.length ile başlık uzunluğunu
            //buldurduktan sonra StringBuffer ile sondaki "A" harfini siliyoruz.
            int sayiii = haber_basligi.length();
            StringBuilder sBuffer = new StringBuilder();
            sBuffer.append(haber_basligi);
            sBuffer.deleteCharAt(sayiii - 1);

            haber_basligi = sBuffer.toString();

            haber_tarihi = elements.select("div[class=yellow-date]").text();

            paragraf_sayisi = elements.select("div[class=text-resizable]").select("p").size();

            for (int i = 0; i < paragraf_sayisi; i++) {

                if (!elements.select("div[class=text-resizable]").select("p").get(i).text().equals("")) {
                    haber_icerigi = haber_icerigi + elements.select("div[class=text-resizable]").select("p").get(i).text() + "\n \n";
                }
            }


            haberdeki_resim_sayisi = elements.select("div[class=text-resizable]").select("img").size();

            for (int i = 0; i < haberdeki_resim_sayisi; i++) {

                resim_linkleri.add(strings[1] + elements.select("div[class=text-resizable]").select("img").get(i).attr("src"));
            }


            haberdeki_link = elements.select("div[class=text-resizable]").select("p").select("a").attr("href");

            tablo_sayisi = elements.select("div[class=text-resizable]").select("ol").select("li").size();

            for (int i = 0; i < tablo_sayisi; i++) {

                haber_icerigi = haber_icerigi + elements.select("div[class=text-resizable]").select("ol").select("li").get(i).text() + "\n \n";

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        interface_dialogbox.async_sonucu(haber_basligi,haber_tarihi,haber_icerigi,haberdeki_link,resim_linkleri);

    }
}

package com.mymoonapplab.oxfirat.dialogBox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymoonapplab.oxfirat.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class dialogBox extends DialogFragment {

    private TextView textView_baslik, textView_tarih, textView_icerik;
    private RecyclerView recyclerView;
    private dialogbox_adapter adapter;
    private String URL_LINKI;

    public dialogBox() {

    }

    @SuppressLint("ValidFragment")
    public dialogBox(String URL_LINKI) {
        this.URL_LINKI = URL_LINKI;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialogbox, container, false);
        new haberi_cek().execute();

        textView_baslik = view.findViewById(R.id.dialogbox_baslik);
        textView_tarih = view.findViewById(R.id.dialogbox_tarih);
        textView_icerik = view.findViewById(R.id.dialogbox_icerik);

        recyclerView = view.findViewById(R.id.dialogbox_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


    @SuppressLint("StaticFieldLeak")
    private class haberi_cek extends AsyncTask<Void, Void, Void> {

        private Elements elements;
        private String haber_basligi;
        private String haber_tarihi;
        private String haber_icerigi;
        private String haberdeki_link;

        private int haberdeki_resim_sayisi;
        private int paragraf_sayisi;

        private ArrayList<String> resim_linkleri;

        private int tablo_sayisi;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            haber_icerigi = "";
            resim_linkleri = new ArrayList<>();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document = Jsoup.connect(URL_LINKI).get();
                elements = document.select("div[class=col-xs-12 col-md-8 lm-md-t7 lm-xs-t6 lm-xs-b6 searchMain]");

                haber_basligi = elements.select("div[class=row]").text();

                haber_tarihi = elements.select("div[class=yellow-date]").text();

                paragraf_sayisi = elements.select("div[class=text-resizable]").select("p").size();

                for (int i = 0; i < paragraf_sayisi; i++) {

                    if (!elements.select("div[class=text-resizable]").select("p").get(i).text().equals("")) {
                        haber_icerigi = haber_icerigi + elements.select("div[class=text-resizable]").select("p").get(i).text() + "\n \n";
                    }
                }


                haberdeki_resim_sayisi = elements.select("div[class=text-resizable]").select("img").size();

                for (int i = 0; i < haberdeki_resim_sayisi; i++) {

                    resim_linkleri.add(getString(R.string.okul_sitesi) + elements.select("div[class=text-resizable]").select("img").get(i).attr("src"));
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Activity activity = getActivity();

            if (activity != null) {
                textView_baslik.setText(haber_basligi);
                textView_tarih.setText(haber_tarihi);
                textView_icerik.setText(haber_icerigi);

                adapter = new dialogbox_adapter(resim_linkleri, getContext());

                recyclerView.setAdapter(adapter);


                if (!haberdeki_link.equals("")) {
                    haberdeki_link = getString(R.string.okul_sitesi) + haberdeki_link;

                    textView_icerik.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(haberdeki_link));
                            startActivity(i);
                        }
                    });
                }
            }
        }
    }
}

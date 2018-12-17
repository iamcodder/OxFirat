package com.example.iamcodder.androidd.etkinlik;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.iamcodder.androidd.MainActivity;
import com.example.iamcodder.androidd.R;
import com.example.iamcodder.androidd.haberler.fragment_haberler;

import org.jsoup.Jsoup;


import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class fragment_etkinlik extends Fragment {

    private ArrayList<String> etkinlik_tarih;
    private ArrayList<String> etkinlik_icerik;
    private ArrayList<String> etkinlik_link;
    private RecyclerView recyclerView;
    private FragmentManager manager;

    private ProgressBar progressBar;

    private WaveSwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new etkinlikCek().execute();
        View rootView=inflater.inflate(R.layout.fragment_etkinlik, container, false);
        recyclerView=rootView.findViewById(R.id.fragment_etkinlik_recyclerview);
        progressBar=rootView.findViewById(R.id.etkinlik_progressbar);

        manager=getFragmentManager();

        swipeRefreshLayout=rootView.findViewById(R.id.fragment_etkinlik_waveswipe);
        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new etkinlikCek().execute();
            }
        });


        return rootView;
    }

    private void etkinlikleri_yerlestir(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

    }


    @SuppressLint("StaticFieldLeak")
    private class etkinlikCek extends AsyncTask<Void,Void,Void>{

        private Elements etkinlikElements;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            etkinlik_tarih=new ArrayList<>();
            etkinlik_icerik=new ArrayList<>();
            etkinlik_link=new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document document=Jsoup.connect("http://www.firat.edu.tr/tr/etkinlikler").get();
                etkinlikElements=document.select("div[class=banner col-xs-12 col-sm-4 col-lg-3");

                for (int i=0;i<etkinlikElements.size();i++){
                    etkinlik_icerik.add(etkinlikElements.get(i).select("div[class=bottom").text());
                    etkinlik_tarih.add(etkinlikElements.get(i).select("span[class=day]").text());
                    etkinlik_link.add(MainActivity.FIRAT_WEB +etkinlikElements.get(i).select("a").attr("href"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            etkinlikleri_yerlestir();

            fragment_etkinlik_adapter adapter=new fragment_etkinlik_adapter(getContext(),etkinlik_tarih,etkinlik_icerik,etkinlik_link,manager);
            recyclerView.setAdapter(adapter);

            progressBar.setVisibility(View.INVISIBLE);
            swipeRefreshLayout.setRefreshing(false);


        }
    }

}

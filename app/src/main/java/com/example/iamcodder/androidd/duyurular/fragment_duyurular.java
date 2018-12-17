package com.example.iamcodder.androidd.duyurular;


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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class fragment_duyurular extends Fragment {

    private ArrayList<String> duyuru_linki,duyuru_icerigi, duyuru_tarihi;
    private RecyclerView recyclerView;
    private fragment_duyurular_adapter adapter;

    private FragmentManager manager;

    private ProgressBar progressBar;

    private WaveSwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new duyuruCek().execute();
        View rootView= inflater.inflate(R.layout.fragment_duyurular, container, false);
        progressBar=rootView.findViewById(R.id.duyurular_progressbar);

        recyclerView=rootView.findViewById(R.id.fragment_duyurular_recyclerview);
        manager=getFragmentManager();

        swipeRefreshLayout=rootView.findViewById(R.id.fragment_duyurular_waveswipe);
        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new duyuruCek().execute();
            }
        });

        return rootView;
    }

    private void duyurulari_yerlestir(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    @SuppressLint("StaticFieldLeak")
    private class duyuruCek extends AsyncTask<Void,Void,Void>{

        private Elements duyuruElements;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            duyuru_linki=new ArrayList<>();
            duyuru_icerigi=new ArrayList<>();
            duyuru_tarihi =new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document=Jsoup.connect("http://www.firat.edu.tr/tr/duyurular").get();
                duyuruElements=document.select("div[class=banner col-xs-12 col-sm-4 col-lg-3]");

                for (int i=0;i<duyuruElements.size();i++){

                    duyuru_icerigi.add(duyuruElements.get(i).select("div[class=top]").text());
                    duyuru_tarihi.add(duyuruElements.get(i).select("span[class=day").text());
                    duyuru_linki.add(MainActivity.FIRAT_WEB +duyuruElements.get(i).select("a").attr("href"));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            duyurulari_yerlestir();

            adapter=new fragment_duyurular_adapter(getContext(),duyuru_icerigi,duyuru_tarihi,duyuru_linki,manager);
            recyclerView.setAdapter(adapter);

            progressBar.setVisibility(View.INVISIBLE);
            swipeRefreshLayout.setRefreshing(false);



        }
    }

}

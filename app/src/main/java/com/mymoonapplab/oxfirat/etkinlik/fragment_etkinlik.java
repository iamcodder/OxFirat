package com.mymoonapplab.oxfirat.etkinlik;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mymoonapplab.oxfirat.MainActivity;
import com.mymoonapplab.oxfirat.R;

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
    private fragment_etkinlik_adapter adapter;
    private ProgressBar progressBar;

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private int page_number;

    private int son_etkinlik_konumu;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_etkinlik, container, false);

        etkinlik_tarih=new ArrayList<>();
        etkinlik_icerik=new ArrayList<>();
        etkinlik_link=new ArrayList<>();

        page_number=1;

        new etkinlikCek().execute();

        progressBar=rootView.findViewById(R.id.etkinlik_progressbar);

        recyclerView=rootView.findViewById(R.id.fragment_etkinlik_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                LinearLayoutManager manager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                int toplam_etkinlik_sayisi=0;
                son_etkinlik_konumu=0;

                if(manager!=null){
                    toplam_etkinlik_sayisi=manager.getItemCount();
                    son_etkinlik_konumu=manager.findLastVisibleItemPosition();
                }

                if(son_etkinlik_konumu+1==toplam_etkinlik_sayisi){
                    new etkinlikCek().execute();
                    progressBar.setVisibility(View.VISIBLE);
                }


            }
        });


        swipeRefreshLayout=rootView.findViewById(R.id.fragment_etkinlik_waveswipe);
        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new etkinlikCek().execute();
            }
        });


        return rootView;
    }



    @SuppressLint("StaticFieldLeak")
    private class etkinlikCek extends AsyncTask<Void,Void,Void>{

        private Elements etkinlikElements;


        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document document=Jsoup.connect("http://www.firat.edu.tr/tr/etkinlikler?page="+page_number).get();
                etkinlikElements=document.select("div[class=banner col-xs-12 col-sm-4 col-lg-3");
                page_number++;

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

            progressBar.setVisibility(View.INVISIBLE);

            adapter=new fragment_etkinlik_adapter(getContext(),etkinlik_tarih,etkinlik_icerik,etkinlik_link,getFragmentManager());
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(son_etkinlik_konumu-1);

            swipeRefreshLayout.setRefreshing(false);

        }
    }

}

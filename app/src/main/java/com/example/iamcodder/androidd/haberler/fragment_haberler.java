package com.example.iamcodder.androidd.haberler;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iamcodder.androidd.MainActivity;
import com.example.iamcodder.androidd.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class fragment_haberler extends Fragment {


    private ArrayList<String> haberBasligi,haberResmi;
    public static ArrayList<String> haberLinki;
    private adapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar bar;

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    public int page_number;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_haberler, container, false);

        page_number=1;

        new haberCek().execute();

        bar=rootView.findViewById(R.id.fragment_haberler_progressBar);

        recyclerView=rootView.findViewById(R.id.fragment_haberler_recycleview);


        swipeRefreshLayout=rootView.findViewById(R.id.fragment_haberler_waveswipe);
        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new haberCek().execute();
            }
        });


        return rootView;
    }

    private void haberleri_yerlestir(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    class haberCek extends AsyncTask<Void,Void,Void>{

        private Elements haber1;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            haberBasligi=new ArrayList<>();
            haberLinki=new ArrayList<>();
            haberResmi=new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document document=Jsoup.connect("http://www.firat.edu.tr/tr/haberler?page="+page_number).get();
                haber1=document.select("div[class=row all-news]").select("div[class=banner col-xs-12 col-sm-4 col-md-4 col-lg-3]");


                for (int i=0;i<haber1.size();i++){

                    haberBasligi.add(haber1.get(i).select("div[class=top]").text());
                    haberLinki.add(MainActivity.FIRAT_WEB +haber1.get(i).select("a").attr("href"));

                    String parcalama[]=haber1.get(i).select("div").attr("style").split("'");
                    haberResmi.add(parcalama[1]);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



            haberleri_yerlestir();

            adapter=new adapter(getContext(),haberBasligi,haberResmi,haberLinki,getFragmentManager());

            bar.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(adapter);


            swipeRefreshLayout.setRefreshing(false);
            page_number++;


        }
    }
}

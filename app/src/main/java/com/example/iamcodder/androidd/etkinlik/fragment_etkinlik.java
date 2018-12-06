package com.example.iamcodder.androidd.etkinlik;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iamcodder.androidd.MainActivity;
import com.example.iamcodder.androidd.R;
import org.jsoup.Jsoup;


import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class fragment_etkinlik extends Fragment {

    private ArrayList<String> etkinlik_tarih;
    private ArrayList<String> etkinlik_icerik;
    private ArrayList<String> etkinlik_link;
    private RecyclerView recyclerView;
    private FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new etkinlikCek().execute();
        View rootView=inflater.inflate(R.layout.fragment_etkinlik, container, false);
        recyclerView=rootView.findViewById(R.id.fragment_etkinlik_recyclerview);

        manager=getFragmentManager();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


        return rootView;
    }

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

            fragment_etkinlik_adapter adapter=new fragment_etkinlik_adapter(getContext(),etkinlik_tarih,etkinlik_icerik,etkinlik_link,manager);
            recyclerView.setAdapter(adapter);

        }
    }

}

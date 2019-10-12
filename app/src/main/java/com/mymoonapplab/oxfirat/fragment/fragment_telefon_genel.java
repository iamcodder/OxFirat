package com.mymoonapplab.oxfirat.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.adapter.fragment_telefon_adapter;
import com.mymoonapplab.oxfirat.constant.statik_class;
import com.mymoonapplab.oxfirat.interfacee.interface_telefon;
import com.mymoonapplab.oxfirat.model.model_telefon_numara;

import java.util.ArrayList;

public class fragment_telefon_genel extends Fragment implements interface_telefon {

    View rootView;

    private ArrayList<model_telefon_numara> liste_genel_telefon;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_telefonlar, container, false);

        if(statik_class.MENU_NUMARA.equals("0")){
            genel_telefon();
        }
        else if(statik_class.MENU_NUMARA.equals("1")){
            fakulte_myo_enstitu();
        }

        recyclerView=rootView.findViewById(R.id.fragment_telefonlar_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(new fragment_telefon_adapter(liste_genel_telefon,getContext(),this));



        return rootView;
    }

    private void genel_telefon(){
        liste_genel_telefon=new ArrayList<>();
        liste_genel_telefon.clear();
        liste_genel_telefon.add(new model_telefon_numara("Üniversite Ana Santrali","+904242370000",""));
        liste_genel_telefon.add(new model_telefon_numara("Öğrenci İşleri Daire Başkanlığı","+904242370000","Dahili Numara : 3251"));
        liste_genel_telefon.add(new model_telefon_numara("Basın Yayın ve Halkla İlişkiler Müdürlüğü","+904242370000","Dahili Numara : 3021"));
        liste_genel_telefon.add(new model_telefon_numara("Kütüphane Daire Başkanlığı","+904242370000","Dahili Numara : 5051"));
        liste_genel_telefon.add(new model_telefon_numara("Misafirhane","+904242370000","Dahili Numara : 6000"));
        liste_genel_telefon.add(new model_telefon_numara("Güvenlik Amirliği","+904242370000",""));
        liste_genel_telefon.add(new model_telefon_numara("Atatürk Konferans Salonu","+904242370000","Dahili Numara : 5015"));
        liste_genel_telefon.add(new model_telefon_numara("Sosyal Merkez","+904242370000","Dahili Numara : 4084"));
        liste_genel_telefon.add(new model_telefon_numara("Üniversite Evi","+904242370000","Dahili Numara : 4327"));
        liste_genel_telefon.add(new model_telefon_numara("ÖSYM Temsilciliği","+904242370000","Dahili Numara : 5020"));
        liste_genel_telefon.add(new model_telefon_numara("FÜBAP","+904242370000","Dahili Numara : 4051"));
        liste_genel_telefon.add(new model_telefon_numara("FÜGEM","+904242370000","Dahili Numara : 3340"));
        liste_genel_telefon.add(new model_telefon_numara("Fırat Üniversitesi Hastanesi","+904242333555",""));

    }

    private void fakulte_myo_enstitu(){
        liste_genel_telefon=new ArrayList<>();
        liste_genel_telefon.clear();
        liste_genel_telefon.add(new model_telefon_numara("Fen Edebiyat Fakültesi","+904242187815",""));
        liste_genel_telefon.add(new model_telefon_numara("Eğitim Fakültesi","+904242362902",""));
        liste_genel_telefon.add(new model_telefon_numara("Teknoloji Fakültesi","+904242184673",""));
        liste_genel_telefon.add(new model_telefon_numara("İletişim Fakültesi","+904242362660",""));
        liste_genel_telefon.add(new model_telefon_numara("İlahiyat Fakültesi","+904242416032",""));
        liste_genel_telefon.add(new model_telefon_numara("Mühendislik Fakültesi","+904242181906",""));
        liste_genel_telefon.add(new model_telefon_numara("Su Ürünleri Fakültesi","+904242122780",""));
        liste_genel_telefon.add(new model_telefon_numara("İktisadi ve İdari Bilimler Fakültesi","+904242332765",""));
        liste_genel_telefon.add(new model_telefon_numara("Tıp Fakültesi","+904242122960",""));
        liste_genel_telefon.add(new model_telefon_numara("Veteriner Fakültesi","+904242128525",""));
        liste_genel_telefon.add(new model_telefon_numara("Beden Eğitimi ve Spor Yüksekokulu","+904242367743",""));
        liste_genel_telefon.add(new model_telefon_numara("Sağlık Yüksekokulu","+904242181778",""));
        liste_genel_telefon.add(new model_telefon_numara("Devlet Konservatuarı","+904242370088",""));
        liste_genel_telefon.add(new model_telefon_numara("Yabancı Diller Yüksekokulu","+904242370063",""));
        liste_genel_telefon.add(new model_telefon_numara("Süleyman Demirel Keban Meslek Yüksekokulu","+904245712302",""));
        liste_genel_telefon.add(new model_telefon_numara("Sivrice Meslek Yüksekokulu","+904244112997",""));
        liste_genel_telefon.add(new model_telefon_numara("Sağlık Hizmetleri Meslek Yüksekokulu","+904242185131",""));
        liste_genel_telefon.add(new model_telefon_numara("Sosyal Bilimler Meslek Yüksekokulu","+904242122781",""));
        liste_genel_telefon.add(new model_telefon_numara("Teknik Bilimler Meslek Yüksekokulu","+904242122404",""));
        liste_genel_telefon.add(new model_telefon_numara("Sağlık Hizmetleri Meslek Yüksekokulu","+904242185131",""));
        liste_genel_telefon.add(new model_telefon_numara("Fen Bilimleri Enstitüsü","+904242122707",""));
        liste_genel_telefon.add(new model_telefon_numara("Sağlık Bilimleri Enstitüsü","+904242122708",""));
        liste_genel_telefon.add(new model_telefon_numara("Sosyal Bilimleri Enstitüsü","+904242122709",""));


    }

    @Override
    public void cardview_tiklama(int position) {
        String numara=liste_genel_telefon.get(position).getTelefon();
        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",numara,null));
        startActivity(intent);
    }
}

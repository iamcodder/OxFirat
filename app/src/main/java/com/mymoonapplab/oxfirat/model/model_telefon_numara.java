package com.mymoonapplab.oxfirat.model;

public class model_telefon_numara {

    private String isim,telefon,dahili;

    public model_telefon_numara(String isim, String telefon, String dahili) {
        this.isim = isim;
        this.telefon = telefon;
        this.dahili = dahili;
    }

    public String getIsim() {
        return isim;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getDahili() {
        return dahili;
    }
}

package com.mymoonapplab.oxfirat.async_task;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.mymoonapplab.oxfirat.R;
import com.mymoonapplab.oxfirat.activity.HomeActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class async_yemekhane_bildirim extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private ArrayList<String> liste_yemek;
    private String tarih="", ogununtarihi="";
    private int boyut;

    public async_yemekhane_bildirim(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        liste_yemek = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect("http://uevi.firat.edu.tr").get();

            Elements elementsListe = document.select("div[class=views-field views-field-body]").select("p");


            for (int i = 1; i < elementsListe.size(); i++) {
                if (elementsListe.get(i) != null && !elementsListe.get(i).text().equals("")) {
                    liste_yemek.add(elementsListe.get(i).text());
                }


            }

            Elements elementsTarih = document.select("span[class=field-content");

            tarih = elementsTarih.get(1).text();

            boyut = tarih.length();

            for (int i = 0; i < tarih.length(); i++) {

                if(tarih.charAt(i)==',' && tarih.charAt(i-2)>0){
                    ogununtarihi=tarih.charAt(i-2)+""+tarih.charAt(i-1);
                }

            }

            if(ogununtarihi.charAt(0)=='0'){
                ogununtarihi=ogununtarihi.charAt(1)+"";
            }

        } catch (IOException e) {
            Log.d("Sülo : ", "hata");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        if(liste_yemek!=null && !liste_yemek.isEmpty()){
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("Settings", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("yemekhane_tarihi", Integer.parseInt(ogununtarihi));


            int bugunun_tarihi=sharedPreferences.getInt("bugünün_tarihi",0);


            if(bugunun_tarihi==Integer.parseInt(ogununtarihi)){
                editor.putBoolean("yemek_cekildi_mi", true);
                bildirim();
            }
            else {
                editor.putBoolean("yemek_cekildi_mi", false);
            }

            editor.apply();

        }

    }

    private void bildirim() {

        NotificationCompat.Builder builder;

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (manager != null) {

            Intent intent = new Intent(mContext, HomeActivity.class);

            //adındanda bahseildiği gibi gidilecek intenti burada giriyoruz
            PendingIntent gidilecekIntent = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            //kanallar artık oreodan sonra bildirimlerin toplandığı bir havuzda toplanıyor
            //o yüzden herhangi bir uygulama için kanal özelliklerini o havuzda toplanması için
            //bilgilendiric bir şekilde veriyoruz
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                String kanal_id = "id_oxfirat";
                String kanal_ad = "oxFırat";
                String kanal_tanim = "uygulama";
                int kanal_onceligi = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel channel = manager.getNotificationChannel(kanal_id);

                //burada kanal boşşsa yani daha önce oluşturulup havuza atılmamışsa özelliklerini vererek
                //oluşturuyoruz
                if (channel == null) {
                    channel = new NotificationChannel(kanal_id, kanal_ad, kanal_onceligi);
                    channel.setDescription(kanal_tanim);
                    manager.createNotificationChannel(channel);
                }

                builder = new NotificationCompat.Builder(mContext, kanal_id);
                builder.setContentTitle("oxFırat");
                builder.setContentText("Günün yemeğine göz attın mı?");
                builder.setSmallIcon(R.drawable.ic_icon_firat);
                builder.setAutoCancel(true); //bildirimdeyken bastığımızda bizi activitye yönlendiriyor ancak
                //bildirim çubuğunda halen gözüküyor.Onun gözülmemesi için true değer veriyoruz
                builder.setStyle(new NotificationCompat.InboxStyle()
                        .addLine(liste_yemek.get(0))
                        .addLine(liste_yemek.get(1))
                        .addLine(liste_yemek.get(2))
                        .addLine(liste_yemek.get(3))
                        .addLine("Afiyet Olsun")
                        .setBigContentTitle("Günün Menüsü")
                        .setSummaryText("OxFırat"));
                //bildirime tıklanıldığında nereye gideceğini buraya giriyoruz
                builder.setContentIntent(gidilecekIntent);

                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setVibrate(new long[] { 1000, 1000});

            } else {


                builder = new NotificationCompat.Builder(mContext);
                builder.setContentTitle("oxFırat");
                builder.setContentText("Günün yemeğine göz attın mı?");
                builder.setSmallIcon(R.drawable.ic_icon_firat);
                builder.setAutoCancel(true); //bildirimdeyken bastığımızda bizi activitye yönlendiriyor ancak
                //bildirim çubuğunda halen gözüküyor.Onun gözülmemesi için true değer veriyoruz
                builder.setStyle(new NotificationCompat.InboxStyle()
                        .addLine(liste_yemek.get(0))
                        .addLine(liste_yemek.get(1))
                        .addLine(liste_yemek.get(2))
                        .addLine(liste_yemek.get(3))
                        .addLine("Afiyet Olsun")
                        .setBigContentTitle("Günün Menüsü")
                        .setSummaryText("OxFırat"));
                //bildirime tıklanıldığında nereye gideceğini buraya giriyoruz
                builder.setContentIntent(gidilecekIntent);

                //burada ekstra olarak bildirim seviyesinin yüksekliğini yazıyoruz
                builder.setPriority(Notification.PRIORITY_HIGH);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setVibrate(new long[] { 1000, 1000});



            }

            manager.notify(1,builder.build());



        }
    }

}

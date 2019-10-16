package com.mymoonapplab.oxfirat.bildirim;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Service_Kontrol extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler handler;
    Runnable runnable;

    IntentFilter filter;
    Broadcast_Internet broadcast_internet;

    Calendar calendar;

    ArrayList<Integer> list_haftasonlari;
    boolean bugun_haftasonu_mu;

    SharedPreferences sharedPreferences;

    int bugunun_tarihi, yemekhane_tarihi;

    @Override
    public void onCreate() {
        super.onCreate();


        list_haftasonlari = new ArrayList<>();

        filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        broadcast_internet = new Broadcast_Internet();
        hafta_sonu_kontrolu();

        calendar = Calendar.getInstance();

        yazma();

        registerReceiver(broadcast_internet, filter);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                yazma();

                bugun_haftasonu_mu = false;

                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                for (int i = 0; i < list_haftasonlari.size(); i++) {
                    if (list_haftasonlari.get(i) == bugunun_tarihi) {
                        bugun_haftasonu_mu = true;
                    }
                }

                if (bugun_haftasonu_mu) {
                    unregisterReceiver(broadcast_internet);
                } else {

                    if (bugunun_tarihi != yemekhane_tarihi) {

                        //Saat 10 olduysa internet açık mı kontrol et.
                        if (hour > 10 && hour < 13) {
                            registerReceiver(broadcast_internet, filter);
                        }
                    }

                }

                handler.postDelayed(runnable, 60000);
            }
        };

        handler.postDelayed(runnable, 10000);
    }

    private void yazma() {

        bugunun_tarihi = calendar.get(Calendar.DAY_OF_MONTH);

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        yemekhane_tarihi = sharedPreferences.getInt("yemekhane_tarihi", 0);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("bugünün_tarihi", bugunun_tarihi);
        editor.apply();
    }


    private void hafta_sonu_kontrolu() {
        List<Date> disable = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int month = cal.get(Calendar.MONTH);
        do {
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
                disable.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } while (cal.get(Calendar.MONTH) == month);

        SimpleDateFormat fmt = new SimpleDateFormat("d");
        for (Date date : disable)
            list_haftasonlari.add(Integer.valueOf(fmt.format(date)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast_internet);
    }

}

package com.mymoonapplab.oxfirat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class yemekhane_servis extends Service {

    Timer zamanlayici;
    Handler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        zamanlayici=new Timer();
        handler=new Handler(Looper.getMainLooper());

        zamanlayici.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                yazdir();
            }
        },0,10000);
    }

    public void yazdir(){

        long zaman=System.currentTimeMillis();
        SimpleDateFormat bilgi=new SimpleDateFormat("dd MMMM yyyy, EEEE / hh:mm");
        final String sonuc=bilgi.format(new Date(zaman));

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),sonuc,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        zamanlayici.cancel();
    }
}

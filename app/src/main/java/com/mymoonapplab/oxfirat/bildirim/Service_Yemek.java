package com.mymoonapplab.oxfirat.bildirim;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Service_Yemek extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler handler;
    Runnable runnable;

    SharedPreferences sharedPreferences;

    boolean yemek_cekildi_mi;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        handler=new Handler();

        runnable=new Runnable() {
            @Override
            public void run() {
                yemek_kontrol();
                if(!yemek_cekildi_mi){
                    new async_yemekhane(getApplicationContext()).execute();
                }

            }
        };

        handler.postDelayed(runnable,1000);
    }

    private void yemek_kontrol(){

        yemek_cekildi_mi=sharedPreferences.getBoolean("yemek_cekildi_mi",false);

    }
}

package com.mymoonapplab.oxfirat.bildirim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Broadcast_Internet extends BroadcastReceiver {

    boolean internet_var_mi, yemek_cekildi_mi;
    Intent intent_service;
    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        yemek_cekildi_mi = sharedPreferences.getBoolean("yemek_cekildi_mi", false);
        internet_var_mi = false;
        intent_service = new Intent(context, Service_Yemek.class);
        isNetworkAvailable(context);

    }

    public void isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); //Sistem ağını dinliyor internet var mı yok mu

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo networkInfo : info) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    internet_var_mi = true;
                }
            }
            if (internet_var_mi) {
                if (!yemek_cekildi_mi) context.startService(intent_service);
                else context.stopService(intent_service);
            } else
                context.stopService(intent_service);

        }

    }
}

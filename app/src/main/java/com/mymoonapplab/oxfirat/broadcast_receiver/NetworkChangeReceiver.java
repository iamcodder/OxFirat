package com.mymoonapplab.oxfirat.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mymoonapplab.oxfirat.interfacee.interface_receiver_network;

public class NetworkChangeReceiver extends BroadcastReceiver {

    interface_receiver_network network;
    private boolean is_connected;

    public NetworkChangeReceiver(interface_receiver_network network){
        this.network=network;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        isNetworkAvailable(context);

    }

    public void isNetworkAvailable(Context context) {
        is_connected=false;

        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); //Sistem ağını dinliyor internet var mı yok mu

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo networkInfo : info) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    is_connected=true;
                }
            }
            if(is_connected) network.is_connected(true);
            else network.is_connected(false);
        }
    }
}


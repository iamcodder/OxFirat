package com.mymoonapplab.oxfirat.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mymoonapplab.oxfirat.interfacee.interface_network_control;

public class NetworkChangeReceiver extends BroadcastReceiver {

    static boolean isConnected = false;
    private interface_network_control network_control;

    public NetworkChangeReceiver(interface_network_control network_control) {
        this.network_control = network_control;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        isNetworkAvailable(context);

    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); //Sistem ağını dinliyor internet var mı yok mu

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo networkInfo : info) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {

                    if (!isConnected) { //internet varsa
                        isConnected = true;
                        network_control.result(true);
                    }
                    return true;
                }
            }
        }
        isConnected = false;
        network_control.result(false);
        return false;
    }
}


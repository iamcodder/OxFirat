package com.mymoonapplab.oxfirat.deneme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mymoonapplab.oxfirat.bildirim.Service_Kontrol;

public class AlarmNotificationReceiver extends BroadcastReceiver {

    private Context context;
    private Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        this.intent=intent;

        context.startService(new Intent(context,Service_Kontrol.class));

    }

}
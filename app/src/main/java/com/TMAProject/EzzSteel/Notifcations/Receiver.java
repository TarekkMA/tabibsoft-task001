package com.TMAProject.EzzSteel.Notifcations;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.TMAProject.EzzSteel.Activities.NotificationActivity;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by TarekkMA on 4/8/16.
 */
public class Receiver extends ParsePushBroadcastReceiver {

    @Override
    protected Class<? extends Activity> getActivity(Context context, Intent intent) {
        return NotificationActivity.class;
    }
}

package com.deepshikha.hideappicon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

/**
 * Created by Pawan.Deshpande on 1/17/2018.
 */

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Toast.makeText(context,"ALarmReceiver",Toast.LENGTH_SHORT).show();

    }
}

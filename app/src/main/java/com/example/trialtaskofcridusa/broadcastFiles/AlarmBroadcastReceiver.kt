package com.example.trialtaskofcridusa.broadcastFiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.widget.Toast
import com.example.trialtaskofcridusa.serviceFiles.AlarmServices
import com.example.trialtaskofcridusa.utils.Utils


class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        val intentService = Intent(context, AlarmServices::class.java)
            .putExtra( Utils.ALARM_TITLE, "Test Alarm Title")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }
}
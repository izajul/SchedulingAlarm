package com.example.trialtaskofcridusa.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.example.trialtaskofcridusa.broadcastFiles.AlarmBroadcastReceiver
import com.example.trialtaskofcridusa.ui.notifications.NotificationsFragment
import com.example.trialtaskofcridusa.utils.FunctionsUtils
import com.example.trialtaskofcridusa.utils.Utils
import java.util.*

class Alarm(var alarmId: Int, var hour: Int?, var minute: Int?, var title: String) {

    fun startAlarm(context: Context){
        var alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmBroadcastReceiver::class.java).putExtra(
            Utils.ALARM_TITLE,
            "test_title_id-$alarmId"
        )
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, alarmIntent, 0)

        var calendar = FunctionsUtils.getCalender(hour,minute)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            //System.currentTimeMillis()+10000, //just for test
            Utils.ALARM_DAILY,
            alarmPendingIntent
        )
        Toast.makeText(context, "Alarm set in " + calendar.time , Toast.LENGTH_LONG).show()
    }

    fun cancelAlarm(context: Context){
        var alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, alarmIntent, 0)
        alarmManager.cancel(alarmPendingIntent)
    }
}
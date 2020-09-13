package com.example.trialtaskofcridusa.activity

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.trialtaskofcridusa.alarm.Alarm
import com.example.trialtaskofcridusa.serviceFiles.AlarmServices
import com.example.trialtaskofcridusa.utils.Utils
import com.example.trialtaskofcridusa.utils.Utils.Companion.NOTIFICATION_ID
import kotlin.random.Random


class NotificationActivity : Activity() {
    private var TAG = "Alarm_Service"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, intent.action + " ac")
        if (intent.action == Utils.ACTION_SNOOZE) {
            var alarm = Alarm(
                Random(Int.MAX_VALUE).nextInt(),
                null,
                null,
                "its snoozed after 5 min"
            )
            alarm.startAlarm(this)
        }
        val alarmServices = Intent(this, AlarmServices::class.java)
        stopService(alarmServices)
        finish() // since finish() is called in onCreate(), onDestroy() will be called immediately
    }

    companion object {
        fun getDismissPendingIntent(notificationId: Int, context: Context?): PendingIntent? {
            val intent = Intent(context, NotificationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(NOTIFICATION_ID, notificationId)
            return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }

        fun getSnoozePendingIntent(context: Context?): PendingIntent? {
            val intent = Intent(context, NotificationActivity::class.java)
            intent.action = Utils.ACTION_SNOOZE
            return PendingIntent.getActivity(context, 0, intent, 0)
        }

    }


}
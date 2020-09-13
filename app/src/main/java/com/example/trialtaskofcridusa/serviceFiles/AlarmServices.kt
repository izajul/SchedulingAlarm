package com.example.trialtaskofcridusa.serviceFiles

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP
import android.os.PowerManager.SCREEN_DIM_WAKE_LOCK
import androidx.core.app.NotificationCompat
import com.example.trialtaskofcridusa.R
import com.example.trialtaskofcridusa.activity.NotificationActivity
import com.example.trialtaskofcridusa.application.App
import com.example.trialtaskofcridusa.utils.Utils
import java.util.*


class AlarmServices : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var title: String

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.quest);
        mediaPlayer.isLooping = true;
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator;
    }

     override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         var powerManager: PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

         var isActiveScreen = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
             powerManager.isInteractive
         } else {
             powerManager.isScreenOn
         }
         if (!isActiveScreen){
             var wl: PowerManager.WakeLock = powerManager.newWakeLock(SCREEN_DIM_WAKE_LOCK or ACQUIRE_CAUSES_WAKEUP,"myApp:notification")
             wl.acquire()
         }

         title = intent?.getStringExtra(Utils.ALARM_TITLE).toString()
         val notificationId: Int = Random().nextInt()
         val snoozePendingIntent: PendingIntent? = NotificationActivity.getSnoozePendingIntent(this)
         val dismissPendingIntent = NotificationActivity.getDismissPendingIntent(
             notificationId,
             this
         )


         val notification: Notification = NotificationCompat.Builder(this, App.CHANNEL_ID)
             .setPriority(NotificationCompat.PRIORITY_HIGH)
             .setContentTitle(title)
             .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
             .setContentText("Ring Ring .. of $title")
             .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
             .addAction(
                 R.drawable.ic_baseline_snooze_24,
                 "Snooze for 5 minutes",
                 snoozePendingIntent
             )
             .addAction(R.drawable.ic_baseline_cancel_24, "Dismiss", dismissPendingIntent)
             .build()

         mediaPlayer.start()

         val pattern = longArrayOf(0, 500, 1000, 500)
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
         } else {

             vibrator.vibrate(pattern, 0)
         }
         startForeground(notificationId, notification)
         return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop();
        vibrator.cancel();
    }

    override fun onBind(p0: Intent?): IBinder? {
       return  null
    }

}

package com.example.trialtaskofcridusa.broadcastFiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SystemRebootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED")) {
            Toast.makeText(context,"System Rebooted",Toast.LENGTH_SHORT)

            TODO("Need Some Statement to start Alarm service again  ")
        }
    }

}
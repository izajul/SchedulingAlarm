package com.example.trialtaskofcridusa.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.trialtaskofcridusa.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

//        val intent = Intent()
//        when {
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
//                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
//                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
//            }
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
//                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
//                intent.putExtra("app_package", packageName)
//                intent.putExtra("app_uid", applicationInfo.uid)
//            }
//            else -> {
//                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                intent.addCategory(Intent.CATEGORY_DEFAULT)
//                intent.data = Uri.parse("package:$packageName")
//            }
//        }
//        startActivity(intent)
    }
}
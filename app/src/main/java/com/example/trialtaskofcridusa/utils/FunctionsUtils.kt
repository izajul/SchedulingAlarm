package com.example.trialtaskofcridusa.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.example.trialtaskofcridusa.broadcastFiles.SystemRebootReceiver
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.util.*
import kotlin.math.min


class FunctionsUtils {
    companion object{
        fun getText(inputFld : TextInputEditText) : String {
            var text : String = ""
            if (inputFld!=null)
                text = inputFld.text.toString()
            return text
        }

        fun enableReceiver(context: Activity){
            setUpReceiver(context,PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
        }

        fun disableReceiver(context: Activity){
            setUpReceiver(context,PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        }

        fun getJArrayFromJObject(jsonObject: JsonObject,key: String): JsonArray{
            var array:JsonArray = jsonObject.get(key).asJsonArray
            return array
        }

        fun getStrFromJsonObject(jsonObject: JsonObject,key: String): String {
            var str: String = ""
            str = jsonObject.get(key).asString
            return str
        }

        fun getJObjectFromJArray(jsonArray: JsonArray,inx:Int): JsonObject{
            var obj: JsonObject = jsonArray.get(inx).asJsonObject!!
            return obj
        }

        fun getCalender (hour:Int?, minute:Int?): Calendar{
            val calendar = Calendar.getInstance()


            if ((hour==null)or(minute==null)){
                calendar.timeInMillis = System.currentTimeMillis()+5*60*1000
            }else {
                calendar.timeInMillis = System.currentTimeMillis()
                calendar[Calendar.HOUR_OF_DAY] = hour!!
                calendar[Calendar.MINUTE] = minute!!
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
            }

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
            }
            return calendar
        }

        fun showTopSnacbar(context: Context,view: View,title:String){
            var snacBar: Snackbar = Snackbar.make(view,title, Snackbar.LENGTH_LONG)
            val view = snacBar.view
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params
            //view.background = context.getDrawable(context,R.drawable.custom_drawable) // for custom background
            snacBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
            snacBar.show()
        }


        private fun setUpReceiver(context:Activity,component_state:Int){
            val receiver = ComponentName(context, SystemRebootReceiver::class.java)
            context.packageManager.setComponentEnabledSetting(
                receiver,
                component_state,
                PackageManager.DONT_KILL_APP
            )
        }



    }
}
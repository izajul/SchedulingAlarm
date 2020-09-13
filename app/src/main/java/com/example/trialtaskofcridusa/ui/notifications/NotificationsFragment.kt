package com.example.trialtaskofcridusa.ui.notifications

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trialtaskofcridusa.R
import com.example.trialtaskofcridusa.adapters.AlarmListAdapter
import com.example.trialtaskofcridusa.alarm.Alarm
import com.example.trialtaskofcridusa.retroFiles.RetroClient
import com.example.trialtaskofcridusa.utils.FunctionsUtils
import com.example.trialtaskofcridusa.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationsFragment : Fragment() , AlarmListAdapter.onToggleSwitch{
     val TAG = "notification_fragment"
     lateinit var recyclerView: RecyclerView
     lateinit var adapter: AlarmListAdapter
     lateinit var retroClient: RetroClient
     lateinit var progressBar:ProgressBar
     @RequiresApi(Build.VERSION_CODES.M)
     override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
     ): View? {
          val root = inflater.inflate(R.layout.fragment_notifications, container, false)
          initClickView(root)
          retroClient = RetroClient().getInstance()!!
          getAlarms() //get available notifications from server
          return root
     }

     @RequiresApi(Build.VERSION_CODES.M)
     private fun initClickView(root: View) {
          progressBar = root.findViewById(R.id.progressbar)
          recyclerView = root.findViewById(R.id.alarmListRC)
          recyclerView.layoutManager = LinearLayoutManager(context)
          adapter = AlarmListAdapter(this)
          recyclerView.adapter = adapter

          var btn: FloatingActionButton = root.findViewById(R.id.fab)
          btn.setOnClickListener {
               val dialogView: View = layoutInflater.inflate(
                    R.layout.bottom_sheet_dialog_layout,
                    null
               )
               val dialog = BottomSheetDialog(context!!)
               dialog.setContentView(dialogView)
               dialog.show()
               addAlarmSchedule(dialog!!)
          }
     }

     @RequiresApi(Build.VERSION_CODES.M)
     private fun addAlarmSchedule(view: Dialog){
          var timePicker: TimePicker = view.findViewById(R.id.timePicker)
          var btn: Button  = view.findViewById(R.id.start)
          Log.d("date_time", timePicker.hour.toString())
          btn.setOnClickListener{
               submitAlarm(timePicker)
               view.dismiss()
          }
     }

     @RequiresApi(Build.VERSION_CODES.M)
     private fun submitAlarm(timePicker: TimePicker) {
          progressBar.visibility = View.VISIBLE
          var params  =  HashMap<String, Any>()
          params[Utils.PRAM_KEY_TIMES] =
               "${timePicker.hour}:${timePicker.minute}" // time picker set time 24h format

          var call: Call<JsonObject?>? = retroClient.getApi().setNotification(params)
          call!!.enqueue(object : Callback<JsonObject?> {
               override fun onResponse(call: Call<JsonObject?>?, response: Response<JsonObject?>?) {
                    var obj: JsonObject = response?.body()!!
                    Log.d(TAG, Gson().toJson(response!!.body()))
                    if (FunctionsUtils.getStrFromJsonObject(
                              obj,
                              Utils.RESP_ERROR_STATUS
                         ) == "false"
                    ) {
                         FunctionsUtils.showTopSnacbar(context!!, view!!, "Alarm Set Successfully")
                         var arr:JsonArray = FunctionsUtils.getJArrayFromJObject(obj,Utils.RESP_DATA)
                         var alarms = FunctionsUtils.getAlarmListFromJArray(activity!!,arr)
                         adapter.setItems(alarms)
                    }
                    progressBar.visibility = View.GONE
               }

               override fun onFailure(call: Call<JsonObject?>?, t: Throwable?) {
                    t?.message?.let {
                         Log.e(TAG, it)
                         FunctionsUtils.showTopSnacbar(context!!, view!!, it)
                    }
                    progressBar.visibility = View.GONE

               }

          })

     }

     private fun getAlarms(){

          var call: Call<JsonObject?>? = retroClient.getApi().getNotification()
          call!!.enqueue(object : Callback<JsonObject?> {
               override fun onResponse(call: Call<JsonObject?>?, response: Response<JsonObject?>?) {
                    var obj: JsonObject = response?.body()!!
                    Log.d(TAG, Gson().toJson(response!!.body()))
                    if (FunctionsUtils.getStrFromJsonObject(
                              obj,
                              Utils.RESP_ERROR_STATUS
                         ) == "false"
                    ) {
                         var arr:JsonArray = FunctionsUtils.getJArrayFromJObject(obj,Utils.RESP_DATA)
                         adapter.setItems(FunctionsUtils.getAlarmListFromJArray(activity!!,arr))
                    }
                    progressBar.visibility = View.GONE
               }

               override fun onFailure(call: Call<JsonObject?>?, t: Throwable?) {
                    t?.message?.let {
                         Log.e(TAG, it)
                         FunctionsUtils.showTopSnacbar(context!!, view!!, it)
                    }
                    progressBar.visibility = View.GONE
               }

          })
     }

     override fun onToggle(alarm: Alarm?) {
          if (alarm?.isStarted!!){
               alarm.cancelAlarm(context!!)
          }else{
               alarm.startAlarm(context!!)
          }
     }

}
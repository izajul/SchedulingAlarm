package com.example.trialtaskofcridusa.ui.notifications

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.trialtaskofcridusa.R
import com.example.trialtaskofcridusa.alarm.Alarm
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random


class NotificationsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        initClickView(root)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initClickView(root: View) {
        recyclerView = root.findViewById(R.id.alarmListRC)
        var btn: FloatingActionButton = root.findViewById(R.id.fab)
        btn.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.bottom_sheet_dialog_layout, null)
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
            var alarm = Alarm(
                Random(Int.MAX_VALUE).nextInt(),
                timePicker.currentHour,
                timePicker.currentMinute,
                "Test Alarm Title"
            )
            alarm.startAlarm(context!!)

        }
    }
}
package com.example.trialtaskofcridusa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trialtaskofcridusa.R
import com.example.trialtaskofcridusa.alarm.Alarm

class AlarmListAdapter(listner: onToggleSwitch):
    RecyclerView.Adapter<AlarmListAdapter.MyViewHolder>() {
    var alarms: MutableList<Alarm> = ArrayList()
    var listner: onToggleSwitch = listner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.alarm_list_row, parent, false)
        return MyViewHolder(itemView,listner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.bind(alarm)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    fun setItems(alarms:List<Alarm>){
        this.alarms = alarms as MutableList<Alarm>
        notifyDataSetChanged()
    }

    interface onToggleSwitch{
        fun onToggle(alarm: Alarm?)
    }

    class MyViewHolder(itemView: View,listner: onToggleSwitch) : RecyclerView.ViewHolder(itemView){
        var listner: onToggleSwitch = listner
        private var alarmTime: TextView? = itemView.findViewById(R.id.alarm_time_tv)
        private var switch: Switch? = itemView.findViewById(R.id.alarm_set_switch)

        fun bind(alarm: Alarm){
            alarmTime?.text  = "${alarm?.hour}"+":"+"${alarm?.minute}"
            switch?.isChecked = alarm.isStarted
            switch!!.setOnCheckedChangeListener { compoundButton, b ->
                listner.onToggle(alarm)
            }
        }
    }

}
package com.example.callingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.callingapp.models.CallLogEntry
import java.text.SimpleDateFormat
import java.util.*

class CallLogAdapter : ListAdapter<CallLogEntry, CallLogAdapter.CallLogViewHolder>(CallLogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call_log, parent, false)
        return CallLogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        val callLog = getItem(position)
        holder.bind(callLog)
    }

    class CallLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val phoneNumber: TextView = itemView.findViewById(R.id.phone_number)
        private val callDate: TextView = itemView.findViewById(R.id.call_date)
        private val callDuration: TextView = itemView.findViewById(R.id.call_duration)

        fun bind(callLog: CallLogEntry) {
            phoneNumber.text = callLog.number
            callDate.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(callLog.date)
            callDuration.text = "${callLog.duration / 60} min"
        }
    }

    class CallLogDiffCallback : DiffUtil.ItemCallback<CallLogEntry>() {
        override fun areItemsTheSame(oldItem: CallLogEntry, newItem: CallLogEntry): Boolean {
            return oldItem.number == newItem.number && oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: CallLogEntry, newItem: CallLogEntry): Boolean {
            return oldItem == newItem
        }
    }
}

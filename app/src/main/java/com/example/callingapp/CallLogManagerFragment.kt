package com.example.callingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.callingapp.models.CallLogEntry

class CallLogManagerFragment : Fragment(R.layout.fragment_call_log_manager) {

    private lateinit var callLogManager: CallLogManager
    private lateinit var callLogAdapter: CallLogAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_call_log_manager, container, false)

        callLogManager = CallLogManager(requireContext())
        recyclerView = view.findViewById(R.id.recycler_view)

        callLogAdapter = CallLogAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = callLogAdapter

        loadCallLogs()

        return view
    }

    private fun loadCallLogs() {
        val result = callLogManager.getCallLogs()

        result.onSuccess { callLogs ->
            if (callLogs is List<CallLogEntry>) {
                callLogAdapter.submitList(callLogs)
            }
        }.onFailure { exception ->
            Toast.makeText(requireContext(), "Error: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}

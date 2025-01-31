package com.example.callingapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class DialerFragment : Fragment() {
    private lateinit var displayText: TextInputEditText
    private var currentNumber = StringBuilder()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                makePhoneCall(currentNumber.toString())
            } else {
                Toast.makeText(context, "Call permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dialer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayText = view.findViewById(R.id.display_number)
        setupDialerButtons(view)
    }

    private fun setupDialerButtons(view: View) {
        val buttonIds = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
            R.id.btn_star, R.id.btn_hash
        )

        for (buttonId in buttonIds) {
            view.findViewById<MaterialButton>(buttonId).setOnClickListener {
                appendNumber((it as MaterialButton).text.toString())
            }
        }

        view.findViewById<FloatingActionButton>(R.id.button_call).setOnClickListener {
            initiateCall()
        }

        view.findViewById<FloatingActionButton>(R.id.button_backspace).setOnClickListener {
            removeLastDigit()
        }
    }

    private fun appendNumber(digit: String) {
        if (currentNumber.length < 15) { // Limit number length
            currentNumber.append(digit)
            updateDisplay()
        }
    }

    private fun removeLastDigit() {
        if (currentNumber.isNotEmpty()) {
            currentNumber.deleteCharAt(currentNumber.length - 1)
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        displayText.setText(currentNumber.toString())
    }

    private fun initiateCall() {
        val number = currentNumber.toString()
        if (number.isNotEmpty()) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                makePhoneCall(number)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }

    private fun makePhoneCall(number: String) {
        try {
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$number")
            }
            startActivity(intent)
        } catch (e: SecurityException) {
            Toast.makeText(context, "Call permission denied", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error making call", Toast.LENGTH_SHORT).show()
        }
    }
}

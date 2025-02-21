package com.example.customkeyboardsystemlevel

import android.inputmethodservice.InputMethodService
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.view.inputmethod.InputConnection

class CustomKeyboardApp : InputMethodService() {

    override fun onCreateInputView(): View {
        val keyboardView = LayoutInflater.from(this).inflate(R.layout.custom_keyboard_layout, null)

        // Configurar eventos de las teclas
        val keys = listOf(
            R.id.key_1 to "1", R.id.key_2 to "2", R.id.key_3 to "3", R.id.key_4 to "4", R.id.key_5 to "5",
            R.id.key_6 to "6", R.id.key_7 to "7", R.id.key_8 to "8", R.id.key_9 to "9", R.id.key_0 to "0",
            R.id.key_a to "A", R.id.key_b to "B", R.id.key_c to "C"
        )

        keys.forEach { (id, value) ->
            keyboardView.findViewById<Button>(id).setOnClickListener {
                sendKey(value)
            }
        }

        return keyboardView
    }

    private fun sendKey(char: String) {
        val inputConnection: InputConnection? = currentInputConnection
        inputConnection?.commitText(char, 1)
    }
}

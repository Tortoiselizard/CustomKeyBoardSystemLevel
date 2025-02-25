package com.example.customkeyboardsystemlevel

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.view.inputmethod.InputConnection
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.BroadcastReceiver
import android.content.IntentFilter

class CustomKeyboardApp : InputMethodService() {

    private val keyboardReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val key = intent?.getStringExtra("key") // Recibe la tecla enviada
            key?.let { sendKey(it) }  // Envia la tecla al input
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Registramos el receiver para escuchar eventos de teclas
        LocalBroadcastManager.getInstance(this).registerReceiver(
            keyboardReceiver, IntentFilter("KEY_PRESSED")
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // Desregistramos el receiver al cerrar el servicio
        LocalBroadcastManager.getInstance(this).unregisterReceiver(keyboardReceiver)
    }

    override fun onCreateInputView(): View {

        val keyboardView = LayoutInflater.from(this).inflate(R.layout.custom_keyboard_layout, null)
        return keyboardView
    }

    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {
        // Iniciar la franja flotante
        if (!isServiceRunning(Keyboard::class.java)) {
            startService(Intent(this, Keyboard::class.java))
        }
    }

    override fun onWindowHidden() {
        // Detener el servicio cuando el teclado se destruye
        stopService(Intent(this, Keyboard::class.java))
        super.onWindowHidden()
    }

    private fun sendKey(char: String) {
        val inputConnection: InputConnection? = currentInputConnection
        inputConnection?.commitText(char, 1)
    }

    // Método para verificar si el servicio está en ejecución
    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}

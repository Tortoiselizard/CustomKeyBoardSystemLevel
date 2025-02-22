package com.example.customkeyboardsystemlevel

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class Keyboard : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var keyboardView: View

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflamos la vista flotante desde un XML
        keyboardView = LayoutInflater.from(this).inflate(R.layout.floating_keyboard, null)

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

        // Configuramos los parámetros para ocupar toda la pantalla
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,  // Ocupar todo el ancho
            WindowManager.LayoutParams.MATCH_PARENT,  // Ocupar todo el alto
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // Permite estar sobre otras apps
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or  // No roba el foco
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or  // Se dibuja en pantalla completa
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,  // Permite interacción con otras vistas
            PixelFormat.TRANSLUCENT  // Hace la vista traslúcida
        )

        layoutParams.gravity = Gravity.TOP  // Posición en la parte superior

        windowManager.addView(keyboardView, layoutParams)
    }

    private fun sendKey(value: String) {
        val intent = Intent("KEY_PRESSED")
        intent.putExtra("key", value)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)  // Enviamos el evento
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(keyboardView)  // Eliminamos la vista cuando se detiene el servicio
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

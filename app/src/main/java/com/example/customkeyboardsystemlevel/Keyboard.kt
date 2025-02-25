package com.example.customkeyboardsystemlevel

import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.widget.FrameLayout
import android.widget.TextView

class Keyboard : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var keyboardView: View

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflamos la vista flotante desde un XML
        keyboardView = FrameLayout(this).apply {
            setBackgroundColor(0x80000000.toInt())
        }

        // Lista de teclas
//        val keys = listOf("1", "2", "3", "A", "B", "C")
        val keys = listOf("1")
        keys.forEach { value ->
            val kewView = createKey(value)
            (keyboardView as FrameLayout).addView(kewView)
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

    // Crear una tecla inflando desde XML
    private fun createKey(value: String): View {
        val inflater = LayoutInflater.from(this)
        val keyView = inflater.inflate(R.layout.key_button, null)

        keyView.findViewById<TextView>(R.id.key_label).text = value
        keyView.setOnClickListener { sendKey(value) }

        return keyView
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

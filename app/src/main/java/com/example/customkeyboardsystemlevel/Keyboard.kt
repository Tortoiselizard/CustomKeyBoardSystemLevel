package com.example.customkeyboardsystemlevel

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

class Keyboard : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflamos la vista flotante desde un XML
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_keyboard, null)

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
        windowManager.addView(floatingView, layoutParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(floatingView)  // Eliminamos la vista cuando se detiene el servicio
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

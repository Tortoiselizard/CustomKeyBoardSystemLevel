package com.example.customkeyboardsystemlevel

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class FloatingBannerService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflamos la vista flotante desde un XML
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_banner, null)

        // Configuramos los parámetros de la ventana flotante
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            100,  // Altura de la franja
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // Permite que esté sobre otras apps
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  // No roba foco
            PixelFormat.TRANSLUCENT
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

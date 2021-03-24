package com.kyleduo.app.klab.m.window

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import com.kyleduo.app.klab.foundation.BaseActivity
import com.kyleduo.app.klab.foundation.extensions.dp2px
import kotlinx.android.synthetic.main.activity_window.*
import java.lang.ref.WeakReference
import kotlin.random.Random


/**
 * @author zhangduo on 3/24/21
 */
class WindowActivity : BaseActivity() {

    private var actViewRef: WeakReference<View>? = null
    private var appViewRef: WeakReference<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_window)

        showActivityWindow.setOnClickListener {
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            wm.addView(
                createWindow("Activity", this).apply {
                    actViewRef = WeakReference(this)
                }, WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.RGBA_8888
                )
            )
        }

        dismissActivityWindow.setOnClickListener {
            actViewRef?.get()?.let {
                val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wm.removeView(it)
                actViewRef = null
            }
        }

        showApplicationWindow.setOnClickListener {
            val hasPermission =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Settings.canDrawOverlays(applicationContext)
                } else {
                    PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        application.packageName
                    )
                }
            if (!hasPermission) {
                Toast.makeText(this@WindowActivity, "No Permission", Toast.LENGTH_SHORT).show()
                requestSettingCanDrawOverlays()
                return@setOnClickListener
            }

            val wm = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }

            appViewRef?.get()?.let {
                wm.removeView(it)
                appViewRef = null
            }

            wm.addView(
                createWindow("Application", application).apply {
                    appViewRef = WeakReference(this)
                }, WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    type,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.RGBA_8888
                ).apply {
                    gravity = Gravity.START
                    y = Random.nextInt(10) * 100
                }
            )
        }

        dismissApplicationWindow.setOnClickListener {
            val wm = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            appViewRef?.get()?.let {
                wm.removeView(it)
                appViewRef = null
            }
        }
    }

    private fun createWindow(text: String, context: Context) = TextView(context).apply {
        setText(text)
        setPadding(10.dp2px().toInt())
        setBackgroundColor(Color.LTGRAY)
        setOnKeyListener { v, keyCode, event ->
            Toast.makeText(this@WindowActivity, "onKey $keyCode", Toast.LENGTH_SHORT).show()
            false
        }
        setOnClickListener {
            Toast.makeText(this@WindowActivity, "Click", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("InlinedApi")
    private fun requestSettingCanDrawOverlays() {
        val sdkInt = Build.VERSION.SDK_INT
        when {
            sdkInt >= Build.VERSION_CODES.O -> {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(intent)
            }
            sdkInt >= Build.VERSION_CODES.M -> {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.parse("package:${application.packageName}")
                startActivity(intent)
            }
            else -> {
            }
        }
    }
}

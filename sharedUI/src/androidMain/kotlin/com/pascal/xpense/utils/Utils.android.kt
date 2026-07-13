package com.pascal.xpense.utils

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.pascal.xpense.ContextUtils

actual fun showToast(msg: String) {
    Toast.makeText(ContextUtils.context, msg, Toast.LENGTH_SHORT).show()
}

actual fun actionShareUrl(url: String?) {
    if (url.isNullOrEmpty()) return

    val context = ContextUtils.context

    Handler(Looper.getMainLooper()).post {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, url)
            }

            val chooser = Intent.createChooser(shareIntent, "Bagikan link ke...")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

actual fun currentTimeMillis(): Long = System.currentTimeMillis()
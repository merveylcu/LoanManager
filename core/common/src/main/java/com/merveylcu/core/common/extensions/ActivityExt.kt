package com.merveylcu.core.common.extensions

import android.app.Activity
import android.content.Intent
import androidx.core.net.toUri

fun Activity.openPhoneCall(phoneNumber: String) {
    if (phoneNumber.trim().isEmpty()) return

    runCatching {
        startActivity(phoneCallIntent(phoneNumber))
    }
}

fun phoneCallIntent(number: String) = Intent(Intent.ACTION_DIAL).apply {
    data = "tel: $number".toUri()
}

fun Activity.openBrowserDirectly(url: String) = runCatching {
    Intent(Intent.ACTION_VIEW).apply {
        data = url.toUri()
    }.also {
        startActivity(it)
    }
}

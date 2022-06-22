package dev.thelumiereguy.helpers.ui

import android.content.Context

fun Context.toDp(px: Int): Float {
    return resources.displayMetrics.density * px
}

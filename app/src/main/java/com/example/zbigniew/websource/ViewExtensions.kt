package com.futuremind.omili.helpers

import android.support.v4.widget.SwipeRefreshLayout
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText


fun View?.hide() {
    this?.visibility = View.GONE
}

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.isVisible() = this?.visibility == View.VISIBLE


fun View?.enable() {
    this?.isEnabled = true
}

fun View?.disable() {
    this?.isEnabled = false
}

fun View?.enableClickable() {
    this?.isClickable = true
}

fun View?.disableClickable() {
    this?.isClickable = false
}


fun View.convertDpToPixel(dp: Int): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    val px = dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    return px.toInt()
}

fun View.convertPixelsToDp(px: Int): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    val dp = px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    return dp.toInt()
}

/**
 * Fix for SwipeRefreshLayout Error
 * http://stackoverflow.com/questions/27057449/when-switch-fragment-with-swiperefreshlayout-during-refreshing-fragment-freezes
 */
fun SwipeRefreshLayout.clear() {
    this.isRefreshing = false
    this.destroyDrawingCache()
    this.clearAnimation()
}

fun EditText.getStringText(): String = this.text.toString()
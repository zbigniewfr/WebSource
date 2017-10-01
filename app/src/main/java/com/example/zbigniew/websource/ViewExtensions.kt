package com.example.zbigniew.websource

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
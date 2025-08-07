package com.example.webview.ui.utility

import android.app.Activity
import android.content.Context
import android.view.View
import android.webkit.JavascriptInterface
import com.google.android.material.snackbar.Snackbar

class WebAppInterface(
    private val activity: Activity,
    private val onClick:()-> Unit
) {
    @JavascriptInterface
    fun onMenuClicked() {
        activity.runOnUiThread {
            onClick()
        }
    }
}
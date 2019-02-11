package com.extrainteger.symbolic.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.extrainteger.symbolic.R
import com.extrainteger.symbolic.SymbolicConstants
import kotlinx.android.synthetic.main.activity_extras.*

class SymbolicExtrasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extras)
        intent?.let {
            showLoginPage(it.getStringExtra(SymbolicConstants.URL), it.getStringExtra(SymbolicConstants.REDIRECT_URI_FIELD))
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showLoginPage(url: String, redirect: String?) {
        horizontal_progressbar.max = 100
        val webSettings = webView.settings
        webSettings.allowFileAccess = false
        webSettings.javaScriptEnabled = true
        webSettings.saveFormData = false
        webView.isVerticalScrollBarEnabled = false
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.isHorizontalScrollBarEnabled = false
        webView.loadUrl(url)
        webView.webChromeClient = WebChromeClientDemo()
        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressbar.visibility = View.GONE
                content.visibility = View.VISIBLE
                horizontal_progressbar.visibility = View.VISIBLE
                horizontal_progressbar.progress = 0
                if (redirect != null) {
                    if (url == redirect) {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                } else {
                    if (url.contains("/login") || url.contains("/home")) {
                        finish()
                    } else if (url.contains("/confirmation")) {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
            }

//            override fun onPageFinished(view: WebView?, url: String) {
//                super.onPageFinished(view, url)
//                content.visibility = View.INVISIBLE
//                progressbar.visibility = View.VISIBLE
//                horizontal_progressbar.visibility = View.GONE
//                horizontal_progressbar.progress = 100
//            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                horizontal_progressbar.visibility = View.GONE
                horizontal_progressbar.progress = 100
            }
        }
    }

    private inner class WebChromeClientDemo : WebChromeClient() {
        override fun onProgressChanged(view: WebView, progress: Int) {
            horizontal_progressbar.progress = progress
        }
    }
}

package com.extrainteger.symbolic.ui

import android.app.Activity
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.extrainteger.symbolic.R
import com.extrainteger.symbolic.SymbolicConstants
import kotlinx.android.synthetic.main.activity_extras.*
import java.net.URL

class SymbolicExtrasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extras)
        clearWebviewCookies()
        intent?.let {
            showLoginPage(it.getStringExtra(SymbolicConstants.URL), it.getStringExtra(SymbolicConstants.REDIRECT_URI_FIELD))
        }
    }

    private fun showLoginPage(url: String, redirect: String) {
        val extraHeaders = HashMap<String, String>()
        horizontal_progressbar.max = 100
        val webSettings = webView.settings
        webSettings.allowFileAccess = false
        webSettings.javaScriptEnabled = true
        webSettings.saveFormData = false
        webView.isVerticalScrollBarEnabled = false
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.isHorizontalScrollBarEnabled = false
        webView.loadUrl(url + "?" + SymbolicConstants.REDIRECT_URI_FIELD + "=" + redirect, extraHeaders)
        webView.webChromeClient = WebChromeClientDemo()
        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressbar.visibility = View.GONE
                content.visibility = View.VISIBLE
                horizontal_progressbar.visibility = View.VISIBLE
                horizontal_progressbar.progress = 0
            }

            override fun onPageFinished(view: WebView?, url: String) {
                super.onPageFinished(view, url)
                if (url.contains("/login")) {
                    content.visibility = View.INVISIBLE
                    progressbar.visibility = View.VISIBLE
                    horizontal_progressbar.visibility = View.GONE
                    horizontal_progressbar.progress = 100
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }

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

    private fun clearWebviewCookies() {
        CookieSyncManager.createInstance(this)
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookie()
    }
}

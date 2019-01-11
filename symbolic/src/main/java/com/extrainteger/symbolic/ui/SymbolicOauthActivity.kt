package com.extrainteger.symbolic.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.extrainteger.symbolic.SymbolicConstants
import com.extrainteger.symbolic.api.SymbolicRestAdapter
import com.extrainteger.symbolic.models.SymbolicToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.*
import com.extrainteger.symbolic.R
import kotlinx.android.synthetic.main.activity_oauth.*

class SymbolicOauthActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)
        clearWebviewCookies()
        showLoginPage(intent)
    }

    private fun getTokenFromProvider(code: String?, intent: Intent) {
        val restAdapter = SymbolicRestAdapter(intent)
        restAdapter.symbolicApi?.getNewAccessToken(code,
                intent.getStringExtra(SymbolicConstants.CLIENT_ID_FIELD),
                intent.getStringExtra(SymbolicConstants.CLIENT_SECRET_FIELD),
                intent.getStringExtra(SymbolicConstants.REDIRECT_URI_FIELD),
                SymbolicConstants.GRANT_TYPE)?.enqueue(object : Callback<SymbolicToken> {

            override fun onResponse(call: Call<SymbolicToken>?, response: Response<SymbolicToken>?) {
                progressbar.visibility = View.GONE
                if (response?.isSuccessful!!) {
                    val tokenIntent = Intent()
                    tokenIntent.putExtra(SymbolicConstants.ACCESS_TOKEN_FIELD, response.body()?.accessToken)
                    tokenIntent.putExtra(SymbolicConstants.REFRESH_TOKEN_FIELD, response.body()?.refreshToken)
                    tokenIntent.putExtra(SymbolicConstants.TOKEN_TYPE_FIELD, response.body()?.tokenType)
                    setResult(RESULT_OK, tokenIntent)
                    finish()
                } else {
                    val tokenIntent = Intent()
                    tokenIntent.putExtra(SymbolicConstants.GET_TOKEN_ERROR_CODE_FIELD, response.code())
                    setResult(RESULT_OK, tokenIntent)
                    finish()
                }
            }

            override fun onFailure(call: Call<SymbolicToken>?, t: Throwable?) {
                progressbar.visibility = View.GONE
                val tokenIntent = Intent()
                tokenIntent.putExtra(SymbolicConstants.GET_TOKEN_EXCEPTION_FIELD, t?.message)
                setResult(RESULT_OK, tokenIntent)
                finish()
            }
        })
    }

    private fun clearWebviewCookies() {
        CookieSyncManager.createInstance(this)
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookie()
    }

    private fun showLoginPage(intent: Intent) {
        val extraHeaders = HashMap<String, String>()
        if (intent.getStringExtra(SymbolicConstants.REFERER_FIELD) != null) {
            extraHeaders[SymbolicConstants.REFERER_FIELD] = intent.getStringExtra(SymbolicConstants.REFERER_FIELD)
        }
        horizontal_progressbar.max = 100
        val webSettings = webView.settings
        webSettings.allowFileAccess = false
        webSettings.javaScriptEnabled = true
        webSettings.saveFormData = false
        webView.isVerticalScrollBarEnabled = false
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.isHorizontalScrollBarEnabled = false
        webView.loadUrl(getLoginUrl(intent), extraHeaders)
        webView.webChromeClient = WebChromeClientDemo()
        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressbar.visibility = View.GONE
                content.visibility = View.VISIBLE
                horizontal_progressbar.visibility = View.VISIBLE
                horizontal_progressbar.progress = 0
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (url!!.startsWith(intent.getStringExtra(SymbolicConstants.REDIRECT_URI_FIELD))) {
                    getTokenFromProvider(getUrlParams(url), intent)
                    content.visibility = View.INVISIBLE
                    progressbar.visibility = View.VISIBLE
                    horizontal_progressbar.visibility = View.GONE
                    horizontal_progressbar.progress = 100
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

    private fun getUrlParams(url: String): String {
        val urlToFind = Uri.parse(url)
        val paramNames = urlToFind.getQueryParameterNames()
        var value = ""
        for (key in paramNames) {
            urlToFind.getQueryParameter(key)?.let { value = it }
        }
        return value
    }

    private fun getLoginUrl(intent: Intent): String {
        return intent.getStringExtra(SymbolicConstants.BASE_URL_FIELD) + "/oauth/authorize" +
                "?" + SymbolicConstants.CLIENT_ID_FIELD + "=" + intent.getStringExtra(SymbolicConstants.CLIENT_ID_FIELD) +
                "&" + SymbolicConstants.RESPONSE_TYPE_FIELD + "=" + SymbolicConstants.RESPONSE_TYPE_VALUE +
                "&" + SymbolicConstants.REDIRECT_URI_FIELD + "=" + intent.getStringExtra(SymbolicConstants.REDIRECT_URI_FIELD) +
                "&" + SymbolicConstants.SCOPE_FIELD + "=" + intent.getStringExtra(SymbolicConstants.SCOPE_FIELD)
    }
}

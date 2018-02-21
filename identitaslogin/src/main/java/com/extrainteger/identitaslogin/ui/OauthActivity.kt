package com.extrainteger.identitaslogin.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.extrainteger.identitaslogin.IdentitasConstants
import com.extrainteger.identitaslogin.api.RestAdapter
import com.extrainteger.identitaslogin.models.AuthToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.extrainteger.identitaslogin.R
import kotlinx.android.synthetic.main.activity_oauth.*
import android.webkit.WebChromeClient




class OauthActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)
        showLoginPage(intent)
    }

    private fun getTokenFromProvider(code: String?, intent: Intent) {
        val restAdapter = RestAdapter()
        restAdapter.apiClient?.getNewAccessToken(code,
                intent.getStringExtra(IdentitasConstants.CLIENT_ID_FIELD),
                intent.getStringExtra(IdentitasConstants.CLIENT_SECRET_FIELD),
                intent.getStringExtra(IdentitasConstants.REDIRECT_URI_FIELD),
                IdentitasConstants.GRANT_TYPE)?.enqueue(object : Callback<AuthToken>{

            override fun onResponse(call: Call<AuthToken>?, response: Response<AuthToken>?) {
                progressbar.visibility = View.GONE
                if (response?.isSuccessful!!){
                    val tokenIntent = Intent()
                    tokenIntent.putExtra(IdentitasConstants.ACCESS_TOKEN_FIELD, response.body()?.accessToken)
                    tokenIntent.putExtra(IdentitasConstants.REFRESH_TOKEN_FIELD, response.body()?.refreshToken)
                    tokenIntent.putExtra(IdentitasConstants.TOKEN_TYPE_FIELD, response.body()?.tokenType)
                    setResult(RESULT_OK, tokenIntent)
                    finish()
                }
                else{
                    val tokenIntent = Intent()
                    tokenIntent.putExtra(IdentitasConstants.GET_TOKEN_ERROR_CODE_FIELD, response.code())
                    setResult(RESULT_OK, tokenIntent)
                    finish()
                }
            }

            override fun onFailure(call: Call<AuthToken>?, t: Throwable?) {
                progressbar.visibility = View.GONE
                val tokenIntent = Intent()
                tokenIntent.putExtra(IdentitasConstants.GET_TOKEN_EXCEPTION_FIELD, t?.message)
                setResult(RESULT_OK, tokenIntent)
                finish()
            }
        })
    }

    private fun showLoginPage(intent: Intent) {
        horizontal_progressbar.max = 100
        val webSettings = webView.settings
        webSettings.allowFileAccess = false
        webSettings.javaScriptEnabled = true
        webSettings.saveFormData = false
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.loadUrl(getLoginUrl(intent))
        webView.webChromeClient = WebChromeClientDemo()
        webView.webViewClient = object : WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressbar.visibility = View.GONE
                content.visibility = View.VISIBLE
                horizontal_progressbar.visibility = View.VISIBLE
                horizontal_progressbar.progress = 0
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (url!!.startsWith(intent.getStringExtra(IdentitasConstants.REDIRECT_URI_FIELD))){
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
            horizontal_progressbar.setProgress(progress)
        }
    }

    private fun getUrlParams(url: String): String{
        val urlToFind = Uri.parse(url)
        val paramNames = urlToFind.getQueryParameterNames()
        var value = ""
        for (key in paramNames) {
            value = urlToFind.getQueryParameter(key)
        }
        return value
    }

    private fun getLoginUrl(intent: Intent): String{
        return IdentitasConstants.LOGIN_URL_VALUE +
                "?"+IdentitasConstants.CLIENT_ID_FIELD + "=" + intent.getStringExtra(IdentitasConstants.CLIENT_ID_FIELD) +
                "&"+IdentitasConstants.RESPONSE_TYPE_FIELD+"="+IdentitasConstants.RESPONSE_TYPE_VALUE +
                "&"+IdentitasConstants.REDIRECT_URI_FIELD+"=" + intent.getStringExtra(IdentitasConstants.REDIRECT_URI_FIELD) +
                "&"+IdentitasConstants.SCOPE_FIELD+"="+intent.getStringExtra(IdentitasConstants.SCOPE_FIELD)
    }
}

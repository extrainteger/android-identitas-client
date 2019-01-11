package com.extrainteger.symbolic

import android.app.Activity

/**
 * Created by ali on 04/12/17.
 */
data class SymbolicConfig(
    val activity: Activity? = null,
    val BASE_URL: String? = null,
    val CLIENT_ID: String? = null,
    val CLIENT_SCRET: String? = null,
    val REDIRECT_URI: String? = null,
    val SCOPES: List<String>? = null,
    val REFERER: String? = null)
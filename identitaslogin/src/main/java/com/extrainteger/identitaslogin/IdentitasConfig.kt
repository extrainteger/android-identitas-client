package com.extrainteger.identitaslogin

import android.app.Activity

/**
 * Created by ali on 04/12/17.
 */
data class IdentitasConfig(
    val activity: Activity? = null,
    val BASE_URL: String? = null,
    val CLIENT_ID: String? = null,
    val CLIENT_SCRET: String? = null,
    val REDIRECT_URI: String? = null,
    val SCOPES: List<String>? = null,
    val REFERER: String? = null)
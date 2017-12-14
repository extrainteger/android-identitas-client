package com.extrainteger.identitaslogin

import android.app.Activity

/**
 * Created by ali on 04/12/17.
 */
data class IdentitasConfig(val activity: Activity, val CLIENT_ID: String, val CLIENT_SCRET: String, val REDIRECT_URI: String, val SCOPES: List<String>)
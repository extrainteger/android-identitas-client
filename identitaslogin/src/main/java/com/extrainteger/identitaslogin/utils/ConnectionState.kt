package com.extrainteger.identitaslogin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo





/**
 * Created by ali on 17/11/17.
 */
class ConnectionState(val context: Context){
    fun isConnected(): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return isConnected
    }

}
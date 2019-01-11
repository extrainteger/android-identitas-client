package com.extrainteger.symbolic.api

import android.content.Intent
import com.extrainteger.symbolic.SymbolicConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

/**
 * Created by ali on 06/12/17.
 */
class SymbolicRestAdapter(intent: Intent) {
    var retrofit: Retrofit? = null
    var symbolicApi: SymbolicApi? = null

    init {
        val httpClient = OkHttpClient.Builder()

        retrofit = Retrofit.Builder()
                .baseUrl(intent.getStringExtra(SymbolicConstants.BASE_URL_FIELD))
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        symbolicApi = retrofit?.create(SymbolicApi::class.java)
    }
}

package com.extrainteger.identitaslogin.api

import android.content.Intent
import com.extrainteger.identitaslogin.IdentitasConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by ali on 06/12/17.
 */
class RestAdapter(intent: Intent){
    var retrofit: Retrofit? = null
    var apiClient: APIClient? = null

    init {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        retrofit = Retrofit.Builder()
                .baseUrl(intent.getStringExtra(IdentitasConstants.BASE_URL_FIELD))
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        apiClient = retrofit?.create(APIClient::class.java)
    }
}

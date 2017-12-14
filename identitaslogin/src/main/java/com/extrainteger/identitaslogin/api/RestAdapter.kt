package com.extrainteger.identitaslogin.api

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.extrainteger.identitaslogin.IdentitasConstants
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import okhttp3.OkHttpClient
import android.content.SharedPreferences
import com.extrainteger.identitaslogin.models.AuthToken
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Created by ali on 06/12/17.
 */
class RestAdapter{
    var retrofit: Retrofit? = null
    var apiClient: APIClient? = null

    init {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
//        httpClient.addInterceptor(object : Interceptor {
//            @Throws(IOException::class)
//            override fun intercept(chain: Interceptor.Chain): Response {
//                val original = chain.request()
//                val originalHttpUrl = original.url()
//
//                val url = originalHttpUrl.newBuilder().build()
//                val requestBuilder = original.newBuilder()
//                        .url(url)
//
//                val request = requestBuilder.build()
//                return chain.proceed(request)
//            }
//        })
//        httpClient.authenticator(customAuthanticator)


        retrofit = Retrofit.Builder()
                .baseUrl(IdentitasConstants.BASE_URL_VALUE)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        apiClient = retrofit?.create(APIClient::class.java)
    }

//    inner class CustomAuthanticator(context: Context): Authenticator{
//        var sessionPrefs: SharedPreferences? = null
//        var editor: SharedPreferences.Editor? = null
//        private val SESSION_PREFS = "session_prefs"
//        private var GRANT_TYPE = "refresh_token"
//        private var REFRESH_TOKEN: String? = null
//
//
//        init {
//            sessionPrefs = context.getSharedPreferences(SESSION_PREFS, MODE_PRIVATE);
//            editor = context.getSharedPreferences(SESSION_PREFS, MODE_PRIVATE).edit();
//
//        }
//
//        override fun authenticate(route: Route?, response: Response?): Request? {
//            if (response?.request()?.header("Authorization") != null) {
//                return null
//            }
//            REFRESH_TOKEN = sessionPrefs?.getString(SESSION_PREFS, REFRESH_TOKEN)
//            val client = OkHttpClient.Builder().build()
//            retrofit = Retrofit.Builder()
//                    .baseUrl(IdentitasConstants.BASE_URL_VALUE)
//                    .client(client)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//
//            val endPoint = retrofit?.create(APIClient::class.java)
//            val refreshTokenResult = endPoint?.getRefreshAccessToken(GRANT_TYPE, Credentials.getClientId(), Credentials.getClientSecret(), REFRESH_TOKEN!!)
//
//            val tokenSuccessResponse = refreshTokenResult?.execute()
//
//            if (tokenSuccessResponse?.body() is AuthToken) {
//                val newAccessToken = tokenSuccessResponse.body()?.accessToken
//                val newRefreshToken = tokenSuccessResponse.body()?.refreshToken
//                editor?.putString("access_token", newAccessToken)
//                editor?.putString("refresh_token", newRefreshToken)
//                editor?.commit()
//
//                val originalRequest = response.request()
//                val originalUrl = originalRequest.url()
//
//                val url = originalUrl.newBuilder()
//                        .setQueryParameter("access_token", newAccessToken)
//                        .build()
//
//                return originalRequest.newBuilder()
//                        .header("Authorization", tokenSuccessResponse.body()!!.accessToken)
//                        .url(url)
//                        .build()
//            } else {
//                return null
//            }
//
//        }
//
//    }
}

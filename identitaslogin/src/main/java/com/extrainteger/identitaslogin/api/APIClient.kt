package com.extrainteger.identitaslogin.api

import com.extrainteger.identitaslogin.models.AuthToken

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIClient {

    @FormUrlEncoded
    @POST("/oauth/token")
    fun getNewAccessToken(
            @Field("code") code: String?,
            @Field("client_id") clientId: String?,
            @Field("client_secret") clientSecret: String?,
            @Field("redirect_uri") redirectUri: String?,
            @Field("grant_type") grantType: String?): Call<AuthToken>

}

package com.extrainteger.symbolic.models

import com.google.gson.annotations.SerializedName

/**
 * Created by ali on 05/12/17.
 */
data class SymbolicToken(
    @SerializedName("access_token") var accessToken: String?,
    @SerializedName("refresh_token") var refreshToken: String?,
    @SerializedName("token_type") var tokenType: String?
)
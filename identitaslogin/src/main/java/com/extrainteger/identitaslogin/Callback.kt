package com.extrainteger.identitaslogin

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ali on 05/12/17.
 */
abstract class Callback<T>{
    abstract fun success(result: Result<T>)

    abstract fun failure(exception: IdentitasException)
}
package com.extrainteger.identitaslogin

/**
 * Created by ali on 05/12/17.
 */
abstract class Callback<T>{
    abstract fun success(result: Result<T>)

    abstract fun failure(exception: SymbolicException)
}
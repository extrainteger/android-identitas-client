package com.extrainteger.identitaslogin

/**
 * Created by ali on 05/12/17.
 */
open class IdentitasException: RuntimeException {
    constructor(detailedMessage: String?): super(detailedMessage)
    constructor(detailedMessage: String?, throwable: Throwable?): super(detailedMessage, throwable)
}
package com.extrainteger.symbolic

/**
 * Created by ali on 05/12/17.
 */
open class SymbolicException: RuntimeException {
    constructor(detailedMessage: String?): super(detailedMessage)
    constructor(detailedMessage: String?, throwable: Throwable?): super(detailedMessage, throwable)
}
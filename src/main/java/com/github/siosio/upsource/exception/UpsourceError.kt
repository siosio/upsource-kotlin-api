package com.github.siosio.upsource.exception

open class UpsourceError(statusCode: Int, code: Int, message: String) :
    RuntimeException("statusCode:$statusCode, code:$code, message:$message")

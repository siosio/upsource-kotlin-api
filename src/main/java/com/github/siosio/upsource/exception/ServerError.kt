package com.github.siosio.upsource.exception

class ServerError(statusCode:Int, code: Int, message: String) : UpsourceError(statusCode, code, message)

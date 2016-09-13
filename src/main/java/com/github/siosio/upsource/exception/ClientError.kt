package com.github.siosio.upsource.exception

class ClientError(statusCode: Int, code: Int, message: String) : UpsourceError(statusCode, code, message)

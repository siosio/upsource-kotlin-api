package com.github.siosio.upsource.internal

data class ErrorResponse(
    val error: ErrorInfo
)

data class ErrorInfo(
    val code: Int,
    val message: String
)

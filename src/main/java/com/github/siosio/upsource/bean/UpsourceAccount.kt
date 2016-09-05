package com.github.siosio.upsource.bean

import com.github.siosio.upsource.extention.*
import java.util.*

data class UpsourceAccount(
    private val user: String,
    private val password: String
) {

  fun toAuthentication(): String {
    return Base64.getEncoder().encode("$user:$password")
  }
  
}


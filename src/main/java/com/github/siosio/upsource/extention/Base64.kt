package com.github.siosio.upsource.extention

import java.util.*

fun Base64.Encoder.encode(input: String): String {
  return this.encode(input.toByteArray(charset("utf-8"))).toString(charset("utf-8"))
}

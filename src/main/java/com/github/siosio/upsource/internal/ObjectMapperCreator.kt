package com.github.siosio.upsource.internal

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*

object ObjectMapperCreator {
  private val objectMapper: ObjectMapper by lazy {
    val tmp = ObjectMapper()
    tmp.registerModule(KotlinModule())
    tmp.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    tmp
  }

  fun create() = objectMapper
}

package com.github.siosio.upsource

import org.apache.http.entity.*

class TestData(val path: String) {

  fun getStringEntity(): StringEntity {
    Thread.currentThread().contextClassLoader.getResourceAsStream(path).use {
      val text = it.bufferedReader(charset("utf-8")).readText()
      return StringEntity(text, ContentType.APPLICATION_JSON)
    }
  }
}

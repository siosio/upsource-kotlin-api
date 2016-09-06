package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.exception.*
import org.apache.http.client.methods.*
import org.apache.http.entity.*
import org.apache.http.impl.client.*
import org.slf4j.*
import java.io.*

internal class UpsourceApi(
    private val server: String,
    private val upsourceAccount: UpsourceAccount,
    private val httpClient: CloseableHttpClient) {

  companion object {
    private val log = LoggerFactory.getLogger(UpsourceApi::class.java)

  }

  fun <R> send(command: Command<out Any, R>): R {

    val post = HttpPost("${server.trimEnd('/')}/~rpc/${command.name}")
    post.setHeader("Authorization", "Basic ${upsourceAccount.toAuthentication()}")

    val objectMapper = ObjectMapperCreator.create()
    val requestBody = objectMapper.writeValueAsString(command.getRequest())

    log.info("api: {}, body:{}", command.name, requestBody)
    post.entity = StringEntity(requestBody, ContentType.APPLICATION_JSON)

    val response = httpClient.execute(post)
    val statusCode = response.statusLine.statusCode

    log.info("response:{}", response.statusLine)
    val bodyText = response.entity.content.bufferedReader(charset("utf-8")).use {
      it.readText()
    }

    if (statusCode == 200) {
      val result = objectMapper.readValue(bodyText, Map::class.java)
      return objectMapper.convertValue(result["result"], command.responseType)
    } else if (statusCode >= 500) {
      throw ServerError("response status line:${response.statusLine}, body:$bodyText")
    } else if (statusCode >= 400) {
      throw ClientError("response status line:${response.statusLine}, body:$bodyText")
    } else {
      throw IllegalStateException("")
    }
  }
}

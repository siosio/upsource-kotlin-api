package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*
import com.github.siosio.upsource.exception.*
import org.apache.http.client.methods.*
import org.apache.http.entity.*
import org.apache.http.impl.client.*
import org.slf4j.*

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

    log.debug("api: {}, body:{}", command.name, requestBody)
    post.entity = StringEntity(requestBody, ContentType.APPLICATION_JSON)

    val response = httpClient.execute(post)
    val statusCode = response.statusLine.statusCode

    log.debug("response:{}", response.statusLine)
    val bodyText = response.entity.content.bufferedReader(charset("utf-8")).use {
      it.readText()
    }

    if (statusCode == 200) {
      val result = objectMapper.readValue(bodyText, Map::class.java)
      return objectMapper.convertValue(result["result"], command.responseType)
    } else if (statusCode >= 400) {
      throw createUpsourceError(statusCode, bodyText)
    } else {
      throw IllegalStateException("statusCode:$statusCode")
    }
  }

  private fun createUpsourceError(statusCode: Int, body: String): UpsourceError {
    val (code, message) = ObjectMapperCreator.create().readValue(body, ErrorResponse::class.java).error
    return if (statusCode >= 500) {
      ServerError(statusCode, code, message)
    } else {
      ClientError(statusCode, code, message)
    }
  }
}

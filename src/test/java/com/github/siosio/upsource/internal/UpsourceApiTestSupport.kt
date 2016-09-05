package com.github.siosio.upsource.internal

import com.github.siosio.upsource.bean.*
import org.apache.http.*
import org.apache.http.client.methods.*
import org.apache.http.impl.client.*
import org.apache.http.message.*
import org.mockito.*
import java.io.*

internal abstract class UpsourceApiTestSupport {

  protected val mockHttpClient: CloseableHttpClient = Mockito.mock(CloseableHttpClient::class.java)

  protected val mockResponse: CloseableHttpResponse = Mockito.mock(CloseableHttpResponse::class.java)

  protected val httpPost: ArgumentCaptor<HttpPost> = ArgumentCaptor.forClass(HttpPost::class.java)

  protected val sut = UpsourceApi("http://testserver", UpsourceAccount("user", "password"), mockHttpClient)

  init {
    Mockito.`when`(mockHttpClient.execute(httpPost.capture())).thenReturn(mockResponse)
    Mockito.`when`(mockResponse.statusLine).thenReturn(BasicStatusLine(HttpVersion(1, 0), 200, ""))
  }

  fun InputStream.readText(): String {
    use {
      return it.bufferedReader(charset("utf-8")).readText()
    }
  }
}

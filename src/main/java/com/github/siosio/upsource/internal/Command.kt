package com.github.siosio.upsource.internal

internal interface Command<REQ, RES> {

  val name: String

  val responseType: Class<RES>

  fun getRequest(): REQ


}

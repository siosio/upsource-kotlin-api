package com.github.siosio.upsource.bean

import com.fasterxml.jackson.annotation.*

enum class ReviewStateEnum(val value: Int) {
  Open(1),
  Closed(2);

  @JsonValue
  fun toValue(): Int = value

  companion object {
    @JsonCreator
    @JvmStatic
    fun forValue(value: Int): ReviewStateEnum {
      return values().firstOrNull {
        it.value == value
      } ?: throw IllegalArgumentException("unknown value:$value")
    }
  }
}

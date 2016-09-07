package com.github.siosio.upsource.bean

import com.fasterxml.jackson.annotation.*

enum class PriorityClassEnum(val value: Int) {
  Authored(1),
  ToReview(2),
  ReadyToClose(3),
  None(4);

  @JsonValue
  fun toValue(): Int = value

  companion object {
    @JsonCreator
    @JvmStatic
    fun forValue(value: Int): PriorityClassEnum {
      return values().firstOrNull {
        it.value == value
      } ?: throw IllegalArgumentException("unknown value:$value")
    }
  }
}

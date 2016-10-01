package com.github.siosio.upsource.bean

import com.fasterxml.jackson.annotation.*

enum class ParticipantStateEnum(val value: Int) {
  Unread(1),
  Read(2),
  Accepted(3),
  Rejected(4);

  @JsonValue
  fun toValue(): Int = value

  companion object {
    @JsonCreator
    @JvmStatic
    fun forValue(value: String): ParticipantStateEnum {
      return values().firstOrNull {
        it.value.toString() == value
      } ?: throw IllegalArgumentException("unknown value:$value")
    }
  }
}

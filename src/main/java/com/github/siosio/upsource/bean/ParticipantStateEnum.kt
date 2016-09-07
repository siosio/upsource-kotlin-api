package com.github.siosio.upsource.bean

import com.fasterxml.jackson.annotation.*

enum class ParticipantStateEnum(val value: Int) {
  Author(1),
  Reviewer(2),
  Watcher(3);

  @JsonValue
  fun toValue(): Int = value

  companion object {
    @JsonCreator
    @JvmStatic
    fun forValue(value: Int): ParticipantStateEnum {
      return values().firstOrNull {
        it.value == value
      } ?: throw IllegalArgumentException("unknown value:$value")
    }
  }
}
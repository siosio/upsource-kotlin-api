package com.github.siosio.upsource.bean

import com.fasterxml.jackson.annotation.*

enum class RoleInReviewEnum(val value: Int) {
  Author(1),
  Reviewer(2),
  Watcher(3);

  @JsonValue
  fun toValue(): Int = value

  companion object {
    @JsonCreator
    @JvmStatic
    fun forValue(value: String): RoleInReviewEnum {
      return values().firstOrNull {
        it.value.toString() == value
      } ?: throw IllegalArgumentException("unknown value:$value")
    }
  }
}

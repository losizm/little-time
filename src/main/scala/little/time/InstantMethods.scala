/*
 * Copyright 2021 Carlos Conyers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package little.time

import java.time.{ Instant, LocalDateTime, ZoneId }
import java.time.temporal.TemporalAmount

/** Provides extension methods for `java.time.Instant` */
implicit class InstantMethods(instant: Instant) extends AnyVal:
  /**
   * Gets `LocalDateTime` by applying time zone.
   *
   * @param zone time zone
   */
  def toLocalDateTime(zone: ZoneId = ZoneId.systemDefault): LocalDateTime =
    instant.atZone(zone).toLocalDateTime

  /**
   * Gets `LocalDateTime` by applying time zone.
   *
   * @param zone time zone
   */
  def toLocalDateTime(zone: String): LocalDateTime =
    toLocalDateTime(ZoneId.of(zone))

  /**
   * Gets instant with specified amount added.
   *
   * @param amount temporal amount
   */
  def +(amount: TemporalAmount): Instant =
    instant.plus(amount)

  /**
   * Gets instant with specified amount subtracted.
   *
   * @param amount temporal amount
   */
  def -(amount: TemporalAmount): Instant =
    instant.minus(amount)

  /**
   * Compares to other instant and returns the lesser value.
   *
   * @param other other instant
   */
  def min(other: Instant): Instant =
    instantOrdering.min(instant, other)

  /**
   * Compares to other instant and returns the greater value.
   *
   * @param other other instant
   */
  def max(other: Instant): Instant =
    instantOrdering.max(instant, other)
